package com.tj.cardsagainsthumanity.client;

import com.tj.cardsagainsthumanity.client.client.ProtocolClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({
        "com.tj.cardsagainsthumanity.client",
        "com.tj.cardsagainsthumanity.server.protocol.io"
})
public class Main implements CommandLineRunner {
    @Autowired
    private ApplicationContext appContext;

    public static void main(String[] args) {
       /* String textColor = OutputWriter.Colors.Foreground.BLACK;
        String bgColor = OutputWriter.Colors.Background.WHITE;
        String str = String.format("Black card:\n%s%s%s%s MORE TEXT", textColor, bgColor, "SOME TEST", OutputWriter.Colors.RESET);
        new StandardOut().writeLine(str);*/
        new SpringApplicationBuilder()
                .sources(Main.class)
                .web(false)
                .build()
                .run(args);
    }

    @Override
    public void run(String... args) {
        ProtocolClient client = appContext.getBean(ProtocolClient.class);
        client.start();
    }
}
