package com.tj.cardsagainsthumanity.server.protocol.impl.message.response.body;

public class GamePlayerResponseBody extends PlayerResponseBody {
    private final Integer score;

    public GamePlayerResponseBody(Integer id, String displayName, String firstName, String lastName, Integer score) {
        super(id, displayName, firstName, lastName);
        this.score = score;
    }

    public Integer getScore() {
        return score;
    }
}
