package com.tj.cardsagainsthumanity.server;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ServerConfigurationTest {

    private ServerConfiguration serverConfig;

    @Before
    public void setUp() throws Exception {
        serverConfig = new ServerConfiguration();
    }

    @Test
    public void getServerSocket() throws IOException {
        ServerSocket socket = serverConfig.getServerSocket();
        assertNotNull(socket);
        assertEquals(socket.getLocalPort(), 8989);

    }
}