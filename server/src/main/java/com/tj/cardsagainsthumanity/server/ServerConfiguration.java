package com.tj.cardsagainsthumanity.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.ServerSocket;

@Configuration
public class ServerConfiguration {
    public static final int SERVER_PORT = 8989;

    @Bean
    public ServerSocket getServerSocket() throws IOException {
        return new ServerSocket(SERVER_PORT);
    }

}
