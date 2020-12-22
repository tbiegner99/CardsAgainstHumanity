package com.tj.cardsagainsthumanity.exceptions;

public class NoRowUpdatedException extends RuntimeException {
    public NoRowUpdatedException(String message) {
        super(message);
    }
}
