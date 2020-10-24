package com.tj.cardsagainsthumanity.server.websocket;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.core.game.RoundDriver;
import com.tj.cardsagainsthumanity.core.game.events.types.GameEvent;
import com.tj.cardsagainsthumanity.core.game.events.types.RoundEvent;
import com.tj.cardsagainsthumanity.models.gameplay.GameRound;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.server.AbstractConnection;
import com.tj.cardsagainsthumanity.server.protocol.CommandProcessor;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.GameStatusCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.GameStatus;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.EmptyResponse;
import com.tj.cardsagainsthumanity.server.protocol.io.impl.JSONSerializer;
import com.tj.cardsagainsthumanity.server.protocol.io.impl.WebSocketProtocolWriter;
import com.tj.cardsagainsthumanity.server.protocol.message.Command;
import com.tj.cardsagainsthumanity.server.protocol.message.Message;
import com.tj.cardsagainsthumanity.server.protocol.message.Response;
import com.tj.cardsagainsthumanity.server.socket.ConnectionContext;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class WebSocketConnection extends AbstractConnection {

    private final WebSocketSession socket;
    private final WebSocketProtocolWriter writer;
    private final JSONSerializer serializer;

     public WebSocketConnection(WebSocketSession socket, WebSocketProtocolWriter writer, CommandProcessor<Command, Response> commandProcessor) {
        super(commandProcessor);
        this.serializer = new JSONSerializer();
        this.socket = socket;
        this.writer = writer;
    }



    public boolean isConnectionAlive() {
        return socket.isOpen();
    }


    public void closeConnection() throws IOException {
        socket.close();
    }

    public void onDataReceived(String data) {
        Message message =serializer.deserializeMessage(data);
        if(message.getMessageType() == Message.Type.COMMAND) {
            Response response = getCommandProcessor().processMessage((Command)message,getConnectionContext());
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
