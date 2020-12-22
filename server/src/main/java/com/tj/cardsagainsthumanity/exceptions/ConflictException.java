package com.tj.cardsagainsthumanity.exceptions;

public class ConflictException extends RuntimeException implements FrontendException {

    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
