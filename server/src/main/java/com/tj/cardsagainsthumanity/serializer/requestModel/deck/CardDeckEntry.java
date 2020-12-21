package com.tj.cardsagainsthumanity.serializer.requestModel.deck;

import javax.validation.constraints.NotNull;

public class CardDeckEntry {
    @NotNull
    private Integer cardId;
    @NotNull
    private Integer packageId;

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }
}
