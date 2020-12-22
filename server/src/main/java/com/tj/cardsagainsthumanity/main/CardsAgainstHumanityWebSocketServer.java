package com.tj.cardsagainsthumanity.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={
        "com.tj.cardsagainsthumanity.server.websocket"
})
@EnableAutoConfiguration
public class CardsAgainstHumanityWebSocketServer {

    public static void main(String[] args) {
        SpringApplication.run(CardsAgainstHumanityWebSocketServer.class, args);
    }
}
