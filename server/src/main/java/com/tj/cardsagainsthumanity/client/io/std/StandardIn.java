package com.tj.cardsagainsthumanity.client.io.std;

import com.tj.cardsagainsthumanity.client.io.InputReader;
import com.tj.cardsagainsthumanity.exceptions.RuntimeInterruptException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Scanner;

@Component
public class StandardIn implements InputReader {

    private final Scanner scanner;

    public StandardIn() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public Integer readInteger() throws RuntimeInterruptException {
        waitUntilInputAvailable();
        return this.scanner.nextInt();
    }

    @Override
    public String readLine() throws RuntimeInterruptException {
        waitUntilInputAvailable();
        return this.scanner.nextLine();
    }

    private void waitUntilInputAvailable() throws RuntimeInterruptException {
        try {
            while (System.in.available() == 0) {
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            throw new RuntimeInterruptException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String readPassword() {
        return new String(System.console().readPassword());
    }

    @Override
    public void clearInput() {
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }
}
