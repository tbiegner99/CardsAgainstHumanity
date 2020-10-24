package com.tj.cardsagainsthumanity.core.game.events.handler;

import com.tj.cardsagainsthumanity.core.game.events.types.RoundEvent;

public interface RoundStartedEventHandler {
    void onRoundStart(RoundEvent round);
}
