package com.tj.cardsagainsthumanity.client.options;

import com.tj.cardsagainsthumanity.client.io.connection.ServerConnection;
import com.tj.cardsagainsthumanity.client.model.GameState;

public class OptionContext {
    private ServerConnection connection;
    private GameState gameState;

    public OptionContext(ServerConnection connection, GameState gameState) {
        this.gameState = gameState;
        this.connection = connection;
    }


    public GameState getGameState() {
        return gameState;
    }

    public ServerConnection getConnection() {
        return connection;
    }
}
