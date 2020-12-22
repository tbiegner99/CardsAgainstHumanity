package com.tj.cardsagainsthumanity.core.game.events.dispatcher;

import com.tj.cardsagainsthumanity.core.game.events.types.GameEvent;
import com.tj.cardsagainsthumanity.core.game.events.types.PlayerEvent;
import com.tj.cardsagainsthumanity.core.game.events.types.RoundEvent;

public interface GameEventDispatcher {


    void dispatchGameStartedEvent(GameEvent handler);

    void dispatchGameOverEvent(GameEvent handler);

    void dispatchGameChangeEvent(GameEvent handler);

    void dispatchRoundStartedEvent(RoundEvent handler);

    void dispatchRoundOverEvent(RoundEvent handler);

    void dispatchRoundChangeEvent(RoundEvent handler);

    void dispatchPlayerChangeEvent(PlayerEvent playerEvent);
}
