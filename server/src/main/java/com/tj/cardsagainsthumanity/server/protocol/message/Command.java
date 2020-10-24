package com.tj.cardsagainsthumanity.server.protocol.message;

public interface Command<A> extends Message {

    String getCommandName();

    A getArguments();
}
