package com.tj.cardsagainsthumanity.models.cards;

public enum CardType {
    WHITE,
    BLACK;

    public String getType() {
        return this.name().toLowerCase();
    }
}
