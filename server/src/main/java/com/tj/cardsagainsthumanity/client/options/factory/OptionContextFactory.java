package com.tj.cardsagainsthumanity.client.options.factory;

import com.tj.cardsagainsthumanity.client.io.connection.ServerConnection;
import com.tj.cardsagainsthumanity.client.options.OptionContext;
import com.tj.cardsagainsthumanity.models.gameStatus.GameStatus;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OptionContextFactory {
    public OptionContext createOptionContext(ServerConnection connection, Optional<Player> player, GameStatus gameState) {
        return new OptionContext(connection, gameState, player);
    }

}
