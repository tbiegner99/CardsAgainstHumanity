package com.tj.cardsagainsthumanity.core.game.events.dispatcher;

import com.tj.cardsagainsthumanity.core.game.events.handler.*;

public interface GameEventRegistry {
    void registerGameStartedHandler(GameStartedEventHandler handler);

    void registerGameOverHandler(GameOverEventHandler handler);

    void registerGameStateChangeHandler(GameStateChangeEventHandler handler);

    void registerRoundStartedHandler(RoundStartedEventHandler handler);

    void registerRoundOverHandler(RoundOverEventHandler handler);

    void registerRoundStateChangeHandler(RoundStateChangeEventHandler handler);

    void registerPlayerStateChangeEvent(PlayerStateChangeHandler handler);
}
