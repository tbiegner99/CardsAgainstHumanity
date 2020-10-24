package com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.models.gameplay.Game;
import com.tj.cardsagainsthumanity.models.gameplay.Player;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class GameStatus {
    private Integer gameId;
    private Game.GameState state;
    private RoundStatus round;
    private Set<RoundWhiteCard> currentHand;


    public GameStatus() {

    }

    protected GameStatus(Integer gameId, Game.GameState state, RoundStatus roundStatus, Set<RoundWhiteCard> handCards) {
        this.currentHand = handCards;
        this.gameId = gameId;
        this.state = state;
        this.round = roundStatus;
    }

    public static Set<RoundWhiteCard> getCardsForPlayer(GameDriver driver, Player currentPlayer) {
        Game game = driver.getGame();
        return game.getPlayerHands().get(currentPlayer)
                .stream()
                .map(card -> new RoundWhiteCard(card.getId(), card.getText()))
                .collect(Collectors.toSet());
    }

    public static GameStatus fromGame(GameDriver driver, Player currentPlayer) {
        Game game = driver.getGame();
        RoundStatus round = RoundStatus.fromRound(driver.getCurrentRound(), currentPlayer);
        Set<RoundWhiteCard> handCards = getCardsForPlayer(driver, currentPlayer);
        return new GameStatus(game.getId(), game.getState(), round, handCards);
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Game.GameState getState() {
        return state;
    }

    public void setState(Game.GameState state) {
        this.state = state;
    }

    public Set<RoundWhiteCard> getCurrentHand() {
        return currentHand;
    }

    public void setCurrentHand(Set<RoundWhiteCard> currentHand) {
        this.currentHand = currentHand;
    }

    public RoundStatus getRound() {
        return round;
    }

    public void setRound(RoundStatus round) {
        this.round = round;
    }

    @Override
    public boolean equals(Object o) {
        GameStatus that = (GameStatus) o;
        return Objects.equals(gameId, that.gameId);
    }

    @Override
    public String toString() {
        return "GameStatus{" +
                "id=" + gameId +
                ", state=" + state +
                ", round=" + round +
                ", currentHand=" + currentHand +
                '}';
    }
}
