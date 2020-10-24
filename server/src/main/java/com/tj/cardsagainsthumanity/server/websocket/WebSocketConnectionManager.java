package com.tj.cardsagainsthumanity.server.websocket;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class WebSocketConnectionManager {
    private Map<String, WebSocketConnection> connections;

    public WebSocketConnectionManager() {
        connections = new HashMap<>();
    }

    public WebSocketConnection getConnection(String sessionId) {
        return connections.get(sessionId);
    }

    public void addConnection(String sessionId, WebSocketConnection connection) {
        connections.put(sessionId, connection);
    }

    public void removeConnection(String sessionId) {
        connections.remove(sessionId);
    }
}
