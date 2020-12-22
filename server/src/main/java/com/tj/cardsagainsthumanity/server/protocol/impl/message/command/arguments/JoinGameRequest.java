package com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments;

public class JoinGameRequest extends PlayerRequest {
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
