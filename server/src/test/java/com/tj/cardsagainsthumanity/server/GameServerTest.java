package com.tj.cardsagainsthumanity.server;

import com.tj.cardsagainsthumanity.server.protocol.CommandProcessor;
import com.tj.cardsagainsthumanity.server.protocol.io.MessageSerializer;
import com.tj.cardsagainsthumanity.server.protocol.io.impl.ProtocolIO;
import com.tj.cardsagainsthumanity.server.socket.PlayerConnection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GameServerTest {
    GameServer mockServer;
    @Mock
    ServerSocket socket;
    @Mock
    Socket mockSocket;
    @Mock
    CommandProcessor commandProcessor;
    @Mock
    MessageSerializer serializer;
    @Mock
    PlayerConnection mockConnection;
    @Mock
    InetAddress mockAddr;
    @Mock
    InputStream inputStream;
    @Mock
    OutputStream outputStream;

    @Before
    public void setUp() throws Exception {
        GameServer server = new GameServer(socket, commandProcessor, serializer);
        mockServer = spy(server);
        when(mockSocket.getInetAddress()).thenReturn(mockAddr);
        when(mockAddr.getHostAddress()).thenReturn("someName");
        when(socket.accept()).thenReturn(mockSocket);
        when(mockSocket.getInputStream()).thenReturn(inputStream);
        when(mockSocket.getOutputStream()).thenReturn(outputStream);
    }

    @Test
    public void run() {
        doAnswer((Answer) invocation -> {
            mockServer.kill();
            return null;
        }).when(mockServer).handleNewConnection();

        mockServer.run();
        verify(mockServer, times(1)).handleNewConnection();
    }

    @Test
    public void testHandleConnection_whenIOException_ignoreError() throws Exception {
        doThrow(new IOException()).when(socket).accept();
        mockServer.handleNewConnection();
        assertEquals(mockServer.getConnections().size(), 0);
    }

    @Test
    public void testHandleConnection() throws Exception {
        doReturn(mockConnection).when(mockServer).acceptConnection();
        mockServer.handleNewConnection();
        verify(mockServer, times(1)).acceptConnection();
        verify(mockConnection, times(1)).startConnection();
        assertEquals(mockServer.getConnections().size(), 1);
        assertTrue(mockServer.getConnections().contains(mockConnection));
    }

    @Test
    public void testAcceptConnection() throws Exception {
        ProtocolIO expectedIO = new ProtocolIO(inputStream, outputStream, serializer);
        PlayerConnection expected = new PlayerConnection("someName", mockServer, expectedIO, expectedIO, commandProcessor);
        PlayerConnection connection = mockServer.acceptConnection();
        verify(socket, times(1)).accept();
        assertEquals(expected, connection);
    }

    @Test
    public void onConnectionClosed_itRemovesConnectionFromOpenConnection() {
        mockServer.registerConnection(mockConnection);
        assertEquals(mockServer.getConnections().size(), 1);
        mockServer.onConnectionClosed(mockConnection);
        assertEquals(mockServer.getConnections().size(), 0);
    }

}