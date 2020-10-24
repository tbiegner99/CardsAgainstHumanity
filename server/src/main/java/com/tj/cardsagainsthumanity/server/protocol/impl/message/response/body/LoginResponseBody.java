package com.tj.cardsagainsthumanity.server.protocol.impl.message.response.body;

public class LoginResponseBody {
    private Integer playerId;
    private String token;

    public LoginResponseBody() {
    }

    public LoginResponseBody(Integer playerId, String token) {
        this();
        setPlayerId(playerId);
        setToken(token);
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
