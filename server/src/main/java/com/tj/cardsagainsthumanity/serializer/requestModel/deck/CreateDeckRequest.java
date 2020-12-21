package com.tj.cardsagainsthumanity.serializer.requestModel.deck;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tj.cardsagainsthumanity.models.gameplay.Player;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

public class CreateDeckRequest {
    @NotNull
    private String name;
    @Null
    @JsonIgnore
    private Player owner;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
