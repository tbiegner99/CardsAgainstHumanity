package com.tj.cardsagainsthumanity.core.game.events.types;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.core.game.events.EventName;

import java.util.Objects;

public class GameEvent<D> extends BaseEvent<EventName.GameEvents, D> {

    private GameDriver game;

    public GameEvent(GameDriver game, EventName.GameEvents eventType) {
        this(game, eventType, null);
    }

    public GameEvent(GameDriver game, EventName.GameEvents eventType, D data) {
        super(eventType, data);
        this.game = game;
    }

    public GameDriver getGame() {
        return game;
    }

    @Override
    public boolean equals(Object o) {
        GameEvent<?> gameEvent = (GameEvent<?>) o;
        return super.equals(o) && Objects.equals(getGame(), gameEvent.getGame());
    }

}
