package com.tj.cardsagainsthumanity.server.protocol.io.impl;

import com.tj.cardsagainsthumanity.server.protocol.io.ProtocolReader;
import com.tj.cardsagainsthumanity.server.protocol.io.ProtocolWriter;
import com.tj.cardsagainsthumanity.server.protocol.message.Command;
import com.tj.cardsagainsthumanity.server.protocol.message.Message;
import com.tj.cardsagainsthumanity.server.protocol.message.Response;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.validation.UnexpectedTypeException;
import java.io.IOException;

public class WebSocketProtocolWriter implements ProtocolWriter {


    private final JSONSerializer serializer;
    private final WebSocketSession socket;

    public WebSocketProtocolWriter(WebSocketSession socket, JSONSerializer serializer) {
        this.socket=socket;
        this.serializer =serializer;
    }

    private void serializeAndSend(Message message) throws IOException{
        String messageString = serializer.serializeMessage(message);
        TextMessage socketMessage = new TextMessage(messageString);
        socket.sendMessage(socketMessage);
    }

    @Override
    public void sendCommand(Command message) throws IOException{
        serializeAndSend(message);
    }

    @Override
    public void sendResponse(Response response) throws IOException{
        serializeAndSend(response);
    }
}
