package com.tj.cardsagainsthumanity.client.io.connection;

import com.tj.cardsagainsthumanity.client.client.GameStateChangeHandler;
import com.tj.cardsagainsthumanity.server.protocol.io.MessageSerializer;
import com.tj.cardsagainsthumanity.server.protocol.io.impl.ProtocolIO;
import com.tj.cardsagainsthumanity.server.protocol.message.Command;
import com.tj.cardsagainsthumanity.server.protocol.message.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Socket;

@Component
public class ServerConnection {
    private final String host;
    private final Integer port;
    private Socket socket;
    private ProtocolIO protocolIO;
    private MessageSerializer serializer;
    private ReaderThread readerThread;

    @Autowired
    public ServerConnection(@Qualifier("serverHost") String host, @Qualifier("serverPort") Integer port, MessageSerializer serializer) {
        this.host = host;
        this.port = port;
        this.serializer = serializer;
    }

    public boolean connect(GameStateChangeHandler changeHandler) throws IOException {
        this.socket = new Socket(host, port);
        this.protocolIO = new ProtocolIO(socket.getInputStream(), socket.getOutputStream(), this.serializer);
        this.readerThread = new ReaderThread(protocolIO, protocolIO, changeHandler);
        readerThread.start();
        return isConnected();
    }

    public boolean isConnected() {
        return socket.isConnected();
    }


    public void sendCommand(Command command) {
        protocolIO.sendCommand(command);
    }

    public <T extends Response> T waitForResponse(Class<T> responseClass, String messageId) {
        Response response = readerThread.waitForResponseId(messageId);
        return protocolIO.convertMessage(response, responseClass);
    }

    public <T extends Response> T waitForResponse(Class<T> baseResponseClass, Command command) {
        sendCommand(command);
        return waitForResponse(baseResponseClass, command.getMessageId());
    }
}
