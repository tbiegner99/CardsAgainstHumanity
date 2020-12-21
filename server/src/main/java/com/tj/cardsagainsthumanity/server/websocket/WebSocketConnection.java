package com.tj.cardsagainsthumanity.server.websocket;

import com.tj.cardsagainsthumanity.models.gameStatus.GameStatusFactory;
import com.tj.cardsagainsthumanity.server.AbstractConnection;
import com.tj.cardsagainsthumanity.server.protocol.CommandProcessor;
import com.tj.cardsagainsthumanity.server.protocol.io.impl.JSONSerializer;
import com.tj.cardsagainsthumanity.server.protocol.io.impl.WebSocketProtocolWriter;
import com.tj.cardsagainsthumanity.server.protocol.message.Command;
import com.tj.cardsagainsthumanity.server.protocol.message.Message;
import com.tj.cardsagainsthumanity.server.protocol.message.Response;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class WebSocketConnection extends AbstractConnection {

    private final WebSocketSession socket;
    private final WebSocketProtocolWriter writer;
    private final JSONSerializer serializer;

    public WebSocketConnection(WebSocketSession socket, WebSocketProtocolWriter writer, CommandProcessor<Command, Response> commandProcessor, GameStatusFactory gameStatusFactory) {
        super(commandProcessor, gameStatusFactory);
        this.serializer = new JSONSerializer();
        this.socket = socket;
        this.writer = writer;
    }


    @Override
    public boolean isConnectionAlive() {
        return socket.isOpen();
    }


    @Override
    public void closeConnection() throws IOException {
        socket.close();
        this.getConnectionContext().getCurrentGame().ifPresent(game -> this.onGameLeft(game));
    }

    public void onDataReceived(String data) {
        Message message = serializer.deserializeMessage(data);
        if (message.getMessageType() == Message.Type.COMMAND) {
            Response response = getCommandProcessor().processMessage((Command) message, getConnectionContext());
            sendResponse(response);
        }
    }

    @Override
    public void sendCommand(Command message) {

        try {
            writer.sendCommand(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendResponse(Response resp) {
        try {
            writer.sendResponse(resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
