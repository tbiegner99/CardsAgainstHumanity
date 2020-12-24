package com.tj.cardsagainsthumanity.server.websocket;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.dao.gameplay.GameDriverDao;
import com.tj.cardsagainsthumanity.models.gameStatus.GameStatusFactory;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.security.auth.PlayerUserDetails;
import com.tj.cardsagainsthumanity.server.protocol.CommandProcessor;
import com.tj.cardsagainsthumanity.server.protocol.io.impl.JSONSerializer;
import com.tj.cardsagainsthumanity.server.protocol.io.impl.WebSocketProtocolWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private final JSONSerializer serializer;
    private final CommandProcessor commandProcessor;
    private WebSocketConnectionManager connectionManager;
    private GameStatusFactory gameStatusFactory;
    private GameDriverDao gameDriverDao;

    public WebSocketHandler(@Qualifier("genericProcessor") @Autowired CommandProcessor commandProcessor, @Autowired WebSocketConnectionManager connectionManager, @Autowired JSONSerializer serializer, @Autowired GameStatusFactory gameStatusFactory, @Autowired GameDriverDao gameDriverDao) {
        this.connectionManager = connectionManager;
        this.gameDriverDao = gameDriverDao;
        this.serializer = serializer;
        this.commandProcessor = commandProcessor;
        this.gameStatusFactory = gameStatusFactory;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        establishConnection(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        WebSocketConnection connection = connectionManager.getConnection(session.getId());
        if (connection != null) {
            connection.closeConnection();
        }
        connectionManager.removeConnection(session.getId());
    }

    private WebSocketConnection createWebSocketConnection(WebSocketSession session) {
        WebSocketProtocolWriter writer = new WebSocketProtocolWriter(session, serializer);
        Player user = null;
        if (session != null) {
            Authentication auth = (Authentication) session.getPrincipal();
            if (auth != null) {
                user = ((PlayerUserDetails) auth.getPrincipal()).getPlayer();
            }
        }
        WebSocketConnection connection = new WebSocketConnection(session, writer, commandProcessor, gameStatusFactory);

        joinCurrentGame(user, session, connection);

        connection.getConnectionContext()
                .login(user);
        return connection;
    }

    private void joinCurrentGame(Player currentPlayer, WebSocketSession session, WebSocketConnection connection) {
        Object currentGame = session.getAttributes().get("currentGame");
        if (currentGame != null) {
            try {
                GameDriver game = gameDriverDao.getGameByCode(currentGame.toString());
                connection.getConnectionContext().setGameHandler(connection);
                connection.getConnectionContext().joinGame(game);
            } catch (Exception e) {

            }
        }
    }


    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        WebSocketConnection connection = connectionManager.getConnection(session.getId());
        if (connection == null) {
            connection = establishConnection(session);
            connection = createWebSocketConnection(session);
            connectionManager.addConnection(session.getId(), connection);
        }
        connection.onDataReceived(message.getPayload());

    }

    private WebSocketConnection establishConnection(WebSocketSession session) {
        WebSocketConnection connection = createWebSocketConnection(session);
        connectionManager.addConnection(session.getId(), connection);
        return connection;
    }
}
