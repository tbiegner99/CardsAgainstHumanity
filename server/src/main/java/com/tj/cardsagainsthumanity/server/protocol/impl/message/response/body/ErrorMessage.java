package com.tj.cardsagainsthumanity.server.protocol.impl.message.response.body;

public class ErrorMessage {
    private String cause;
    private String message;

    public ErrorMessage(String message, String cause) {
        this.cause = cause;
        this.message = message;
    }

    public ErrorMessage(String message) {
        this.message = message;
    }

    public String getCause() {
        return cause;
    }

    public String getMessage() {
        return message;
    }
}
