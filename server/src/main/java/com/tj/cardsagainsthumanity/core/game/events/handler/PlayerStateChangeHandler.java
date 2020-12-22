package com.tj.cardsagainsthumanity.core.game.events.handler;

import com.tj.cardsagainsthumanity.core.game.events.types.PlayerEvent;

public interface PlayerStateChangeHandler {
    void onPlayerStateChange(PlayerEvent event);
}
