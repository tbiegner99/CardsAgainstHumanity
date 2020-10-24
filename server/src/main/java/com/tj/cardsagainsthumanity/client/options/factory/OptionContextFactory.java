package com.tj.cardsagainsthumanity.client.options.factory;

import com.tj.cardsagainsthumanity.client.io.connection.ServerConnection;
import com.tj.cardsagainsthumanity.client.model.GameState;
import com.tj.cardsagainsthumanity.client.options.OptionContext;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.GameStatus;
import org.springframework.stereotype.Component;

@Component
public class OptionContextFactory {
    public OptionContext createOptionContext(ServerConnection connection, GameState gameState) {
        return new OptionContext(connection, gameState);
    }

    public OptionContext createOptionContextFromGameStatus(ServerConnection connection, GameState previousState, GameStatus gameStatus) {
        GameState newState = GameState.builder(previousState)
                .fromStatus(gameStatus)
                .build();
        return createOptionContext(connection, newState);
    }
}
