package com.tj.cardsagainsthumanity.core.game.gameDriver;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.core.game.RoundDriver;
import com.tj.cardsagainsthumanity.core.game.RoundType;
import com.tj.cardsagainsthumanity.core.game.events.EventFactory;
import com.tj.cardsagainsthumanity.core.game.events.dispatcher.GameEventManager;
import com.tj.cardsagainsthumanity.core.game.events.handler.*;
import com.tj.cardsagainsthumanity.core.game.events.types.GameEvent;
import com.tj.cardsagainsthumanity.core.game.events.types.PlayerEvent;
import com.tj.cardsagainsthumanity.core.game.events.types.RoundEvent;
import com.tj.cardsagainsthumanity.core.game.factory.impl.RoundDriverFactory;
import com.tj.cardsagainsthumanity.core.game.handManager.DrawStrategy;
import com.tj.cardsagainsthumanity.core.game.handManager.HandManager;
import com.tj.cardsagainsthumanity.dao.CardDao;
import com.tj.cardsagainsthumanity.dao.gameplay.GameDriverDao;
import com.tj.cardsagainsthumanity.models.cards.WhiteCard;
import com.tj.cardsagainsthumanity.models.gameplay.CardPlay;
import com.tj.cardsagainsthumanity.models.gameplay.Game;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.models.gameplay.game.PlayerHandCard;
import com.tj.cardsagainsthumanity.models.gameplay.game.Scoreboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class NormalGameDriver implements GameDriver {
    private final GameEventManager eventManager;
    private final EventFactory eventFactory;
    private Game game;
    private RoundDriver currentRound;
    private GameDriverDao store;
    private CardDao cardDao;
    private CzarGenerator czarGenerator;
    private RoundDriverFactory roundDriverFactory;
    private HandManager<WhiteCard> handManager;
    private DrawStrategy drawStrategy;


    public NormalGameDriver(Game game, HandManager<WhiteCard> handManager, DrawStrategy<WhiteCard> drawStrategy, EventFactory eventFactory, GameEventManager eventManager, GameDriverDao store, CardDao cardDao, RoundDriverFactory roundDriverFactory) {
        this.roundDriverFactory = roundDriverFactory;
        this.game = game;
        this.store = store;
        this.eventFactory = eventFactory;
        this.cardDao = cardDao;
        this.handManager = handManager;
        this.eventManager = eventManager;
        this.czarGenerator = new CzarGenerator(game.getPlayers());
        this.drawStrategy = drawStrategy;
        this.currentRound = roundDriverFactory.createGameRound(this, game.getCurrentRound(), eventManager, eventFactory);


    }

    @Override
    public void start() {
        game.setState(Game.GameState.STARTED);
        czarGenerator.shuffle();
        nextRound();
        GameEvent evt = eventFactory.createGameStartedEvent(this);
        eventManager.dispatchGameStartedEvent(evt);
    }

    @Override
    public void end() {
        game.setState(Game.GameState.OVER);
        this.save();
        GameEvent evt = eventFactory.createGameOverEvent(this);
        eventManager.dispatchGameOverEvent(evt);
    }

    @Override
    public void pause() {
        game.setState(Game.GameState.PAUSED);
        this.save();
        GameEvent evt = eventFactory.createGameChangeEvent(this);
        eventManager.dispatchGameChangeEvent(evt);
    }

    @Override
    public void resume() {
        game.setState(Game.GameState.STARTED);
        this.save();
        GameEvent evt = eventFactory.createGameChangeEvent(this);
        eventManager.dispatchGameChangeEvent(evt);

    }

    @Override
    public List<Player> getCzarOrder() {
        return this.czarGenerator.getCzarOrder();
    }

    @Override
    public Integer getCzarPositionFor(Integer playerId) {
        return czarGenerator.getCzarIndexForPlayer(playerId);
    }

    @Override
    public void save() {
        store.saveGame(this);
    }

    @Override
    public Game getGame() {
        return game;
    }

    @Override
    public Integer getGameId() {
        return game.getId();
    }

    @Override
    public String getCode() {
        return game.getCode();
    }

    @Override
    public Game.GameState getState() {
        return game.getState();
    }

    @Override
    public RoundDriver getCurrentRound() {
        return currentRound;
    }

    @Override
    public RoundDriver nextRound() {
        return nextRound(this.getDefaultRoundType());
    }

    @Override
    public RoundDriver nextRound(RoundType type) {
        return createNewRound(type);
    }

    private void updateScores() {
        Scoreboard roundScores = currentRound.getScoresForPlayers();
        this.getScore().addScores(roundScores);
    }

    private RoundDriver createNewRound(RoundType type) {
        RoundDriver round = roundDriverFactory.createGameRound(this, eventManager, eventFactory, type, czarGenerator);
        round.save();
        this.currentRound = round;
        game.setCurrentRound(round.getRound());
        this.save();
        RoundEvent evt = eventFactory.createRoundStartedEvent(this, round);
        eventManager.dispatchRoundStartedEvent(evt);
        return round;
    }


    @Override
    public Scoreboard getScore() {
        return game.getScoreboard();
    }

    @Override
    public void addPlayer(Player player) {
        if (game.hasPlayer(player)) {
            return;
        }
        handManager.addPlayer(player);
        czarGenerator.addPlayer(player);
        handManager.fillOutHandForPlayer(player, drawStrategy);
        game.setPlayerHands(handManager.getAllCardsInHand());
        Set<PlayerHandCard> addedCards = handManager.getCardsInHandForPlayer(player)
                .stream()
                .map(card -> new PlayerHandCard(game, player, card))
                .collect(Collectors.toSet());
        game.getCardsInPlay().addAll(addedCards);
        game.addPlayerToScoreboard(player);
        this.save();
        PlayerEvent evt = eventFactory.createPlayerCreatedEvent(this, player);
        eventManager.dispatchPlayerChangeEvent(evt);
    }

    @Override
    public void replacePlayedCards(Collection<CardPlay> allCardPlays) {
        allCardPlays.forEach(cardPlay -> {
            handManager.removeCardsFromHand(cardPlay.getPlayer(), cardPlay.getCards());
        });
        handManager.fillOutHandsForAllPlayers(drawStrategy);
    }

    @Override
    public void addDisplay() {

    }

    @Override
    public void addAudienceMember() {

    }

    @Override
    public boolean isPlayerInGame(Player player) {
        return czarGenerator.isPlayerInGame(player.getId());
    }

    @Override
    public void removePlayer(Player player) {
        handManager.removePlayer(player);
        game.removePlayer(player);
        this.save();
        czarGenerator.removePlayer(player);
    }

    @Override
    public List<Player> getPlayers() {
        return new ArrayList<>(game.getPlayers());
    }

    @Override
    public void onRoundOver(RoundDriver round) {
        this.updateScores();
        nextRound();
        this.save();
        GameEvent evt = eventFactory.createGameChangeEvent(this);
        eventManager.dispatchGameChangeEvent(evt);
    }

    private RoundType getDefaultRoundType() {
        return RoundType.NORMAL;
    }

    @Override
    public void registerGameStartedHandler(GameStartedEventHandler handler) {
        eventManager.registerGameStartedHandler(handler);
    }

    @Override
    public void registerGameOverHandler(GameOverEventHandler handler) {
        eventManager.registerGameOverHandler(handler);
    }

    @Override
    public void registerGameStateChangeHandler(GameStateChangeEventHandler handler) {
        eventManager.registerGameStateChangeHandler(handler);
    }

    @Override
    public void registerRoundStartedHandler(RoundStartedEventHandler handler) {
        eventManager.registerRoundStartedHandler(handler);
    }

    @Override
    public void registerRoundOverHandler(RoundOverEventHandler handler) {
        eventManager.registerRoundOverHandler(handler);
    }

    @Override
    public void registerRoundStateChangeHandler(RoundStateChangeEventHandler handler) {
        eventManager.registerRoundStateChangeHandler(handler);
    }

    @Override
    public void registerPlayerStateChangeEvent(PlayerStateChangeHandler handler) {
        eventManager.registerPlayerStateChangeEvent(handler);
    }

    @Override
    public void unregisterGameStartedHandler(GameStartedEventHandler handler) {
        eventManager.unregisterGameStartedHandler(handler);
    }

    @Override
    public void unregisterGameOverHandler(GameOverEventHandler handler) {
        eventManager.unregisterGameOverHandler(handler);
    }

    @Override
    public void unregisterGameStateChangeHandler(GameStateChangeEventHandler handler) {
        eventManager.unregisterGameStateChangeHandler(handler);
    }

    @Override
    public void unregisterRoundStartedHandler(RoundStartedEventHandler handler) {
        eventManager.unregisterRoundStartedHandler(handler);
    }

    @Override
    public void unregisterRoundOverHandler(RoundOverEventHandler handler) {
        eventManager.unregisterRoundOverHandler(handler);
    }

    @Override
    public void unregisterRoundStateChangeHandler(RoundStateChangeEventHandler handler) {
        eventManager.unregisterRoundStateChangeHandler(handler);
    }

    @Override
    public void unregisterPlayerStateChangeEvent(PlayerStateChangeHandler handler) {
        eventManager.unregisterPlayerStateChangeEvent(handler);
    }
}
