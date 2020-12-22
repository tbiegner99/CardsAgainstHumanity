package com.tj.cardsagainsthumanity.models.cards;

import java.util.List;

public class DetailedCardPackage {
    private CardPackage packageInfo;
    private List<WhiteCard> whiteCards;
    private List<BlackCard> blackCards;

    public DetailedCardPackage(CardPackage packageInfo, List<WhiteCard> whiteCards, List<BlackCard> blackCards) {
        this.packageInfo = packageInfo;
        this.blackCards = blackCards;
        this.whiteCards = whiteCards;
    }

    public CardPackage getPackageInfo() {
        return packageInfo;
    }

    public List<WhiteCard> getWhiteCards() {
        return whiteCards;
    }

    public List<BlackCard> getBlackCards() {
        return blackCards;
    }
}
