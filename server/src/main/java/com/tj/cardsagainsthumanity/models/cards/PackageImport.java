package com.tj.cardsagainsthumanity.models.cards;

import java.util.Objects;
import java.util.Set;

public class PackageImport {
    private CardPackage cardPackage;
    private Set<Card> cardsToImport;

    public PackageImport(CardPackage cardPackage, Set<Card> cardsToImport) {
        this.cardPackage = cardPackage;
        this.cardsToImport = cardsToImport;
    }

    public CardPackage getCardPackage() {
        return cardPackage;
    }

    public Set<Card> getCardsToImport() {
        return cardsToImport;
    }

    @Override
    public boolean equals(Object o) {
        PackageImport that = (PackageImport) o;
        return Objects.equals(getCardPackage(), that.getCardPackage()) &&
                Objects.equals(getCardsToImport(), that.getCardsToImport());
    }

}
