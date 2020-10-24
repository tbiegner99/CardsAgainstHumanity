package com.tj.cardsagainsthumanity.main;

import com.tj.cardsagainsthumanity.server.GameServer;
import com.tj.cardsagainsthumanity.server.ServerConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan({
        "com.tj.cardsagainsthumanity.core",
        "com.tj.cardsagainsthumanity.dao",
        "com.tj.cardsagainsthumanity.models",
        "com.tj.cardsagainsthumanity.security",
        "com.tj.cardsagainsthumanity.serializer",
        "com.tj.cardsagainsthumanity.server",
        "com.tj.cardsagainsthumanity.services",
        "com.tj.cardsagainsthumanity.utils"
})
@EntityScan("com.tj.cardsagainsthumanity.models")
public class CardsAgainstHumanitySocketServer implements CommandLineRunner {
    @Autowired
    private ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplicationBuilder()
                .sources(CardsAgainstHumanitySocketServer.class)
                .web(false)
                .build();
        app.run(args);
    }

    @Override
    public void run(String... strings) throws Exception {
        GameServer server = context.getBean(GameServer.class);
        Thread serverThread = new Thread(server, "Server thread");
        serverThread.start();
        System.out.println("Server listening on: " + ServerConfiguration.SERVER_PORT);
    }
}
