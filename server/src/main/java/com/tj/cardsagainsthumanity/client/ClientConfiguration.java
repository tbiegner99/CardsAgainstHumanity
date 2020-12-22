package com.tj.cardsagainsthumanity.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.tj.cardsagainsthumanity.server.ServerConfiguration.SERVER_PORT;

@Configuration
public class ClientConfiguration {
    @Bean("serverHost")
    public String getServerHost() {
        return "localhost";
    }

    @Bean("serverPort")
    public Integer getServerPort() {
        return SERVER_PORT;
    }

    @Bean("maxLoginAttempts")
    public Integer getMaxLoginAttempts() {
        return 3;
    }
}
