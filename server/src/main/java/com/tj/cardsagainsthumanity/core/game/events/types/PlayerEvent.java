package com.tj.cardsagainsthumanity.core.game.events.types;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.core.game.events.EventName;
import com.tj.cardsagainsthumanity.models.gameplay.Player;

import java.util.Objects;

public class PlayerEvent<D> extends BaseEvent<EventName.PlayerEvents, D> {
    private final Player player;
    private final GameDriver game;

    public PlayerEvent(GameDriver game, Player player, EventName.PlayerEvents eventType) {
        this(game, player, eventType, null);
    }

    public PlayerEvent(GameDriver game, Player player, EventName.PlayerEvents event, D data) {
        super(event, data);
        this.player = player;
        this.game = game;
    }

    public GameDriver getGame() {
        return game;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean equals(Object o) {
        PlayerEvent<?> that = (PlayerEvent<?>) o;
        return super.equals(o) &&
                Objects.equals(getPlayer(), that.getPlayer()) &&
                Objects.equals(getGame(), that.getGame());
    }
    
}
