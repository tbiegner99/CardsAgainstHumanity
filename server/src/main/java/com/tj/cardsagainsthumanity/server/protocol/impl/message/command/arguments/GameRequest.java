package com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments;

import java.util.Objects;

public class GameRequest {
    private Integer gameId;

    private String gameCode;

    public GameRequest(Integer gameId) {
        setGameId(gameId);
    }

    public GameRequest() {
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    @Override
    public boolean equals(Object o) {
        GameRequest that = (GameRequest) o;
        return Objects.equals(getGameId(), that.getGameId());
    }

}
