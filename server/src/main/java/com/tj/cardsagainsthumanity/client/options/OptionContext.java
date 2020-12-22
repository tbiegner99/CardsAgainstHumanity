package com.tj.cardsagainsthumanity.client.options;

import com.tj.cardsagainsthumanity.client.io.connection.ServerConnection;
import com.tj.cardsagainsthumanity.models.gameStatus.GameStatus;
import com.tj.cardsagainsthumanity.models.gameplay.Player;

import java.util.Optional;

public class OptionContext {
    private ServerConnection connection;
    private GameStatus gameState;
    private Optional<Player> currentPlayer;

    public OptionContext(ServerConnection connection, GameStatus gameState, Optional<Player> currentPlayer) {
        this.gameState = gameState;
        this.connection = connection;
        this.currentPlayer = currentPlayer;
    }


    public GameStatus getGameState() {
        return gameState;
    }

    public ServerConnection getConnection() {
        return connection;
    }

    public Optional<Player> getPlayer() {
        return currentPlayer;
    }

    public boolean isPlayerGameManager() {
        return false; //TODO
    }
}
