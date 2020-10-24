package com.tj.cardsagainsthumanity.client.model;

import com.tj.cardsagainsthumanity.models.gameplay.Game;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.GameStatus;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundStatus;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundWhiteCard;

import java.util.Collection;
import java.util.Optional;

public class GameState {
    private Optional<Integer> currentPlayerId;
    private Optional<String> currentPlayerToken;
    private Optional<Integer> currentGameId;
    private boolean playerGameManager = false;
    private Optional<Game.GameState> state;
    private Optional<RoundStatus> currentRound;
    private Optional<Collection<RoundWhiteCard>> cardsInHand;

    private GameState() {
        state = Optional.empty();
        currentPlayerId = Optional.empty();
        currentPlayerToken = Optional.empty();
        currentGameId = Optional.empty();
        currentRound = Optional.empty();
        cardsInHand = Optional.empty();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(GameState base) {
        return new Builder(base);
    }

    public static GameState initialState() {
        return new GameState();
    }

    public Optional<Integer> getCurrentPlayerId() {
        return currentPlayerId;
    }

    public Optional<String> getCurrentPlayerToken() {
        return currentPlayerToken;
    }

    public Optional<Integer> getCurrentGameId() {
        return currentGameId;
    }

    public Optional<Game.GameState> getState() {
        return state;
    }

    public Optional<RoundStatus> getCurrentRound() {
        return currentRound;
    }

    public boolean isPlayerGameManager() {
        return playerGameManager;
    }

    public Optional<Collection<RoundWhiteCard>> getCardsInHand() {
        return cardsInHand;
    }

    public static class Builder {
        GameState result;

        Builder() {
            result = new GameState();
        }

        Builder(GameState gameState) {
            this();
            if (gameState.getCurrentPlayerId().isPresent()) {
                this.setCurrentPlayer(gameState.getCurrentPlayerId().get(), gameState.getCurrentPlayerToken().get());
            }
            gameState.getCurrentGameId().ifPresent(this::setCurrentGameId);
            gameState.getState().ifPresent(this::setState);
            gameState.getCurrentRound().ifPresent(this::setCurrentRound);
            this.setPlayerIsGameManager(gameState.isPlayerGameManager());
        }

        public Builder setPlayerIsGameManager(boolean playerIsGameManager) {
            result.playerGameManager = playerIsGameManager;
            return this;
        }

        public Builder setState(Game.GameState state) {
            result.state = Optional.ofNullable(state);
            return this;
        }

        public Builder setCurrentGameId(Integer currentGameId) {
            result.currentGameId = Optional.ofNullable(currentGameId);
            return this;
        }

        public Builder setCurrentPlayer(Integer currentPlayerId, String currentPlayerToken) {
            result.currentPlayerToken = Optional.ofNullable(currentPlayerToken);
            result.currentPlayerId = Optional.ofNullable(currentPlayerId);
            return this;
        }

        public Builder setCurrentRound(RoundStatus round) {
            result.currentRound = Optional.ofNullable(round);
            return this;
        }

        public Builder setCardsInHand(Collection<RoundWhiteCard> hand) {
            result.cardsInHand = Optional.ofNullable(hand);
            return this;
        }

        public Builder fromStatus(GameStatus gameStatus) {
            return setCurrentRound(gameStatus.getRound())
                    .setState(gameStatus.getState())
                    .setCardsInHand(gameStatus.getCurrentHand())
                    .setCurrentGameId(gameStatus.getGameId());
        }

        public GameState build() {
            return result;
        }


    }
}
