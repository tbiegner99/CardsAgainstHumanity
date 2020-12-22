package com.tj.cardsagainsthumanity.core.game.events;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.core.game.RoundDriver;
import com.tj.cardsagainsthumanity.core.game.events.types.GameEvent;
import com.tj.cardsagainsthumanity.core.game.events.types.PlayerEvent;
import com.tj.cardsagainsthumanity.core.game.events.types.RoundEvent;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import org.springframework.stereotype.Component;

@Component
public class EventFactory {
    public GameEvent createGameStartedEvent(GameDriver game) {
        return new GameEvent(game, EventName.GameEvents.GAME_STARTED);
    }

    public GameEvent createGameOverEvent(GameDriver game) {
        return new GameEvent(game, EventName.GameEvents.GAME_OVER);
    }

    public GameEvent createGameChangeEvent(GameDriver game) {
        return new GameEvent(game, EventName.GameEvents.GAME_STATE_CHANGE);
    }

    public RoundEvent createRoundChangeEvent(GameDriver game, RoundDriver round) {
        return new RoundEvent(game, round, EventName.RoundEvents.ROUND_STATE_CHANGE);
    }

    public RoundEvent createRoundStartedEvent(GameDriver game, RoundDriver round) {
        return new RoundEvent(game, round, EventName.RoundEvents.ROUND_STARTED);
    }

    public RoundEvent createRoundOverEvent(GameDriver game, RoundDriver round) {
        return new RoundEvent(game, round, EventName.RoundEvents.ROUND_OVER);
    }


    public PlayerEvent createPlayerHandChangeEvent(GameDriver game, Player player) {
        return new PlayerEvent(game, player, EventName.PlayerEvents.PLAYER_HAND_CHANGED);
    }


    public PlayerEvent createPlayerCreatedEvent(GameDriver game, Player player) {
        return new PlayerEvent(game, player, EventName.PlayerEvents.PLAYER_CREATED);
    }
}
