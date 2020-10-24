package com.tj.cardsagainsthumanity.client.client;

import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.GameStatus;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundStatus;

public interface GameStateChangeHandler {
    void onGameStateChanged(GameStatus gameStatus);

    void onRoundStateChanged(RoundStatus roundStatus);
}
