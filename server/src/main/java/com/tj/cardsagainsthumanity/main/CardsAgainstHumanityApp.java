package com.tj.cardsagainsthumanity.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({
        "com.tj.cardsagainsthumanity.core",
        "com.tj.cardsagainsthumanity.dao",
        "com.tj.cardsagainsthumanity.models",
        "com.tj.cardsagainsthumanity.security",
        "com.tj.cardsagainsthumanity.serializer",
        "com.tj.cardsagainsthumanity.controllers",
        "com.tj.cardsagainsthumanity.server.websocket",
        "com.tj.cardsagainsthumanity.server.protocol",
        "com.tj.cardsagainsthumanity.services",
        "com.tj.cardsagainsthumanity.utils"
})
@EntityScan("com.tj.cardsagainsthumanity.models")
public class CardsAgainstHumanityApp {

    public static void main(String[] args) {
        SpringApplication.run(CardsAgainstHumanityApp.class, args);
    }
}
