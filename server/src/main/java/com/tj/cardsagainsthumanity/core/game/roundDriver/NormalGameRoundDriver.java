package com.tj.cardsagainsthumanity.core.game.roundDriver;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.core.game.RoundDriver;
import com.tj.cardsagainsthumanity.core.game.events.EventFactory;
import com.tj.cardsagainsthumanity.core.game.events.EventName;
import com.tj.cardsagainsthumanity.core.game.events.dispatcher.GameEventDispatcher;
import com.tj.cardsagainsthumanity.core.game.events.types.RoundEvent;
import com.tj.cardsagainsthumanity.dao.gameplay.GameRoundDao;
import com.tj.cardsagainsthumanity.models.cards.WhiteCard;
import com.tj.cardsagainsthumanity.models.gameplay.CardPlay;
import com.tj.cardsagainsthumanity.models.gameplay.GameRound;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.models.gameplay.game.Scoreboard;
import com.tj.cardsagainsthumanity.models.gameplay.game.Voter;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class NormalGameRoundDriver implements RoundDriver {
    private final GameEventDispatcher eventDispatcher;
    private final EventFactory eventFactory;
    private GameDriver game;
    private GameRound round;
    private GameRoundDao roundDao;

    public NormalGameRoundDriver(GameDriver game, GameEventDispatcher eventDispatcher, EventFactory eventFactory, GameRound round, GameRoundDao roundDao) {
        this.game = game;
        this.round = round;
        this.roundDao = roundDao;
        this.eventDispatcher = eventDispatcher;
        this.eventFactory = eventFactory;
    }

    @Override
    public void save() {
        round = roundDao.saveGameRound(round);
    }

    @Override
    public boolean areAllCardsIn() {
        return round.getPlays().size() == game.getNumberOfPlayers() - 1; // -1 because czar doesnt play
    }

    @Override
    public Scoreboard getScoresForPlayers() {
        Scoreboard scores = new Scoreboard();
        CardPlay winner = round.getWinner();
        if (winner != null) {
            scores.addScore(game.getGame(), winner.getPlayer(), 1);
        }

        return scores;
    }

    @Override
    public void playCards(CardPlay play) {
        round.addCardPlay(play);
        save();
        RoundEvent roundChangeEvent = new RoundEvent(game, this, EventName.RoundEvents.ROUND_STATE_CHANGE);
        eventDispatcher.dispatchRoundChangeEvent(roundChangeEvent);
    }

    @Override
    public void revealPlay(Player czar) {
        if (!czar.isCzarFor(round)) {
            throw new IllegalStateException("You are not the czar for this round");
        }
        round.revealNext();
        save();
        RoundEvent roundChangeEvent = new RoundEvent(game, this, EventName.RoundEvents.ROUND_STATE_CHANGE);
        eventDispatcher.dispatchRoundChangeEvent(roundChangeEvent);
    }

    @Override
    public void playCards(Player player, List<WhiteCard> cards) {
        playCards(new CardPlay(round, player, cards));
    }

    @Override
    public void declareWinner(Player czar, CardPlay play) {
        if (!czar.isCzarFor(round)) {
            throw new IllegalStateException("You are not the czar for this round");
        }
        round.setWinner(play);

        // endRound();
        save();
        RoundEvent roundChangeEvent = new RoundEvent(game, this, EventName.RoundEvents.ROUND_STATE_CHANGE);
        eventDispatcher.dispatchRoundChangeEvent(roundChangeEvent);
    }

    @Override
    public void declareWinner(Player czar, Integer playId) {
        CardPlay winningPlay = round.getPlayById(playId);
        declareWinner(czar, winningPlay);
    }

    @Override
    public void endRound() {
        CardPlay play = round.getWinner();
        if (play == null) {
            throw new IllegalStateException("Round cannot be ended. No Winner was declared");
        }
        game.replacePlayedCards(getAllCardPlays());
        RoundEvent evt = eventFactory.createRoundOverEvent(game, this);
        eventDispatcher.dispatchRoundOverEvent(evt);
        game.onRoundOver(this);


    }

    @Override
    public boolean isRoundOver() {
        return round.getWinner() != null;
    }

    @Override
    public void upvote(Voter voter, CardPlay play) {

    }

    @Override
    public void downvote(Voter voter, CardPlay play) {

    }

    @Override
    public GameRound getRound() {
        return round;
    }

    @Override
    public Collection<CardPlay> getAllCardPlays() {
        return round.getPlays();
    }

    @Override
    public boolean equals(Object o) {
        NormalGameRoundDriver that = (NormalGameRoundDriver) o;
        return game == that.game &&
                Objects.equals(getRound(), that.getRound());
    }

    @Override
    public GameDriver getGame() {
        return game;
    }

    @Override
    public Optional<CardPlay> getPlayForPlayer(Player currentPlayer) {
        return getAllCardPlays().stream()
                .filter(play -> play.getPlayer().getId() == currentPlayer.getId())
                .findFirst();
    }
}
