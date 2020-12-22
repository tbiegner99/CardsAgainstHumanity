package com.tj.cardsagainsthumanity.server.socket;

public interface ConnectionCloseHandler {
    void onConnectionClosed(PlayerConnection connection);
}
