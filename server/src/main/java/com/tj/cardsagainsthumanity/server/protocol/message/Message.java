package com.tj.cardsagainsthumanity.server.protocol.message;

public interface Message {
    Type getMessageType();

    String getMessageId();

    enum Type {
        COMMAND,
        RESPONSE
    }
}