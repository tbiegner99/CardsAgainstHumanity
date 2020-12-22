package com.tj.cardsagainsthumanity.client.io.std;

import com.tj.cardsagainsthumanity.client.io.OutputWriter;
import org.fusesource.jansi.AnsiConsole;
import org.springframework.stereotype.Component;

@Component
public class StandardOut implements OutputWriter {
    public StandardOut() {
        AnsiConsole.systemInstall();
    }

    @Override
    public void write(String message) {
        System.out.print(message);
    }

    @Override
    public void writeLine(String message) {
        System.out.println(message);
    }
}
