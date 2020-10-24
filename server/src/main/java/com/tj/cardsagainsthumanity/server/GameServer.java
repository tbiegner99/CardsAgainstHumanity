package com.tj.cardsagainsthumanity.server;

import com.tj.cardsagainsthumanity.server.protocol.CommandProcessor;
import com.tj.cardsagainsthumanity.server.protocol.io.MessageSerializer;
import com.tj.cardsagainsthumanity.server.protocol.io.impl.ProtocolIO;
import com.tj.cardsagainsthumanity.server.socket.ConnectionCloseHandler;
import com.tj.cardsagainsthumanity.server.socket.PlayerConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class GameServer implements Runnable, ConnectionCloseHandler {
    private ServerSocket socket;
    private CommandProcessor genericProcessor;
    private MessageSerializer serializer;
    private boolean killed = false;

    private Set<PlayerConnection> openConnections;

    @Autowired
    public GameServer(ServerSocket socket, @Qualifier("genericProcessor") CommandProcessor genericProcessor, MessageSerializer serializer) {
        this.genericProcessor = genericProcessor;
        this.socket = socket;
        this.openConnections = new HashSet<>();
        this.serializer = serializer;
    }

    public Set<PlayerConnection> getConnections() {
        return Collections.unmodifiableSet(openConnections);
    }


    @Override
    public void run() {
        while (!killed) {
            handleNewConnection();
        }
    }

    public void kill() {
        killed = true;
    }

    void handleNewConnection() {
        try {
            waitForConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
            //TODO:log
        }
    }

    private void waitForConnection() throws IOException {
        PlayerConnection conn = acceptConnection();
        conn.startConnection();
        registerConnection(conn);
    }

    PlayerConnection acceptConnection() throws IOException {
        Socket client = socket.accept();
        client.setSoTimeout(100);
        ProtocolIO socketIO = createIO(client);
        String connectionName = client.getInetAddress().getHostAddress();
        return new PlayerConnection(connectionName, this, socketIO, socketIO, genericProcessor);
    }

    private ProtocolIO createIO(Socket socket) throws IOException {
        return new ProtocolIO(socket.getInputStream(), socket.getOutputStream(), serializer);
    }

    void registerConnection(PlayerConnection conn) {
        openConnections.add(conn);
    }

    @Override
    public void onConnectionClosed(PlayerConnection connection) {
        openConnections.remove(connection);
    }
}
