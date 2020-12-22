package com.tj.cardsagainsthumanity.core.game.events.types;

import com.tj.cardsagainsthumanity.core.game.events.EventName;

import java.util.Objects;

public abstract class BaseEvent<T extends EventName, D> {

    private T eventType;
    private D data;
    

    public BaseEvent(T eventType, D data) {
        this.data = data;
        this.eventType = eventType;
    }


    public D getData() {
        return data;
    }

    public T getEventType() {
        return eventType;
    }

    @Override
    public boolean equals(Object o) {
        BaseEvent<?, ?> baseEvent = (BaseEvent<?, ?>) o;
        return Objects.equals(getEventType(), baseEvent.getEventType()) &&
                Objects.equals(getData(), baseEvent.getData());
    }

}
