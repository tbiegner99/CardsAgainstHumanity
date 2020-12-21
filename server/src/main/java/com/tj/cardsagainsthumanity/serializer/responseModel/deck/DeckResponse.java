package com.tj.cardsagainsthumanity.serializer.responseModel.deck;

import com.tj.cardsagainsthumanity.serializer.responseModel.gameplay.PlayerResponse;

public class DeckResponse {
    private Integer id;
    private String name;
    private PlayerResponse owner;
    private Integer whiteCardCount;
    private Integer blackCardCount;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlayerResponse getOwner() {
        return owner;
    }

    public void setOwner(PlayerResponse owner) {
        this.owner = owner;
    }

    public Integer getWhiteCardCount() {
        return whiteCardCount;
    }

    public void setWhiteCardCount(Integer whiteCardCount) {
        this.whiteCardCount = whiteCardCount;
    }

    public Integer getBlackCardCount() {
        return blackCardCount;
    }

    public void setBlackCardCount(Integer blackCardCount) {
        this.blackCardCount = blackCardCount;
    }
}
