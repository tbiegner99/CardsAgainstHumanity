package com.tj.cardsagainsthumanity.server.protocol.message;

public interface Command<A> extends Message {

    default boolean isLoginRequired() {
        return true;
    }

    String getCommandName();

    A getArguments();
}
