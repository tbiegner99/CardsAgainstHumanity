package com.tj.cardsagainsthumanity.core.game.events;

public interface EventName {
    public static enum GameEvents implements EventName {
        GAME_STARTED,
        GAME_OVER,
        GAME_STATE_CHANGE,
    }

    public static enum RoundEvents implements EventName {
        ROUND_STARTED,
        ROUND_OVER,
        ROUND_STATE_CHANGE
    }

    public static enum PlayerEvents implements EventName {
        PLAYER_HAND_CHANGED,
        PLAYER_CREATED
    }


}
