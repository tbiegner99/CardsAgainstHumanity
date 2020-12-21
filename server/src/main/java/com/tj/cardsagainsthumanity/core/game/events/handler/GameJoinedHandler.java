package com.tj.cardsagainsthumanity.core.game.events.handler;

import com.tj.cardsagainsthumanity.core.game.GameDriver;

public interface GameJoinedHandler {
    void onGameJoined(GameDriver game);

    void onGameLeft(GameDriver game);
}
