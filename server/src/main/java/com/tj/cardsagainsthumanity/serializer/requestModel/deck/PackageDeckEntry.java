package com.tj.cardsagainsthumanity.serializer.requestModel.deck;

import javax.validation.constraints.NotNull;

public class PackageDeckEntry {
    private Integer deckId;
    @NotNull
    private Integer packageId;

    public Integer getDeckId() {
        return deckId;
    }

    public void setDeckId(Integer deckId) {
        this.deckId = deckId;
    }

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }
}
