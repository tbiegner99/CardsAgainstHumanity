package com.tj.cardsagainsthumanity.core.game.events.types;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.core.game.RoundDriver;
import com.tj.cardsagainsthumanity.core.game.events.EventName;

import java.util.Objects;

public class RoundEvent<D> extends BaseEvent<EventName.RoundEvents, D> {
    private final GameDriver game;
    private final RoundDriver round;

    public RoundEvent(GameDriver game, RoundDriver round, EventName.RoundEvents eventType) {
        this(game, round, eventType, null);
    }

    public RoundEvent(GameDriver game, RoundDriver round, EventName.RoundEvents eventType, D data) {
        super(eventType, data);
        this.game = game;
        this.round = round;

    }

    public GameDriver getGame() {
        return game;
    }

    public RoundDriver getRound() {
        return round;
    }

    @Override
    public boolean equals(Object o) {
        RoundEvent<?> that = (RoundEvent<?>) o;
        return super.equals(o) &&
                Objects.equals(getGame(), that.getGame()) &&
                Objects.equals(getRound(), that.getRound());
    }

}
