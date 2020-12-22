package com.tj.cardsagainsthumanity.client.io;

import com.tj.cardsagainsthumanity.exceptions.RuntimeInterruptException;

public interface InputReader {
    Integer readInteger() throws RuntimeInterruptException;

    String readLine() throws RuntimeInterruptException;

    String readPassword();

    void clearInput();
}
