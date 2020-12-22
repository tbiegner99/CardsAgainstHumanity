package com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments;

public class CreateGameRequest {
    private Integer playerId;
    private Integer deckId = 1;

    public Integer getDeckId() {
        return deckId;
    }

    public void setDeckId(Integer deckId) {
        this.deckId = deckId;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }
}
