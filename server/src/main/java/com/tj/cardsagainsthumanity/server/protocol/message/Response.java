package com.tj.cardsagainsthumanity.server.protocol.message;

public interface Response<T> extends Message {
    int getStatus();

    String getStatusMessage();

    T getBody();

    boolean isErrorResponse();
}
