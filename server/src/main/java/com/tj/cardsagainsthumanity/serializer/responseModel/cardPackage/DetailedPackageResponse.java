package com.tj.cardsagainsthumanity.serializer.responseModel.cardPackage;

import java.util.List;

public class DetailedPackageResponse {
    private PackageResponse packageInfo;
    private List<CardResponse> whiteCards;
    private List<CardResponse> blackCards;

    public DetailedPackageResponse(PackageResponse packageInfo, List<CardResponse> whiteCards, List<CardResponse> blackCards) {
        this.packageInfo = packageInfo;
        this.whiteCards = whiteCards;
        this.blackCards = blackCards;
    }

    public PackageResponse getPackageInfo() {
        return packageInfo;
    }

    public List<CardResponse> getWhiteCards() {
        return whiteCards;
    }

    public List<CardResponse> getBlackCards() {
        return blackCards;
    }
}
