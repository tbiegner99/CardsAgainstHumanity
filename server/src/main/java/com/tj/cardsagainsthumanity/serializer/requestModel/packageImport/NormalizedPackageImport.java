package com.tj.cardsagainsthumanity.serializer.requestModel.packageImport;

import com.tj.cardsagainsthumanity.serializer.requestModel.card.CreateCardRequest;
import com.tj.cardsagainsthumanity.serializer.requestModel.cardPackage.CreatePackageRequest;

import java.util.Collection;

public class NormalizedPackageImport {
    private CreatePackageRequest packageInfo;
    private Collection<CreateCardRequest> whiteCards;
    private Collection<CreateCardRequest> blackCards;

    public CreatePackageRequest getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(CreatePackageRequest packageInfo) {
        this.packageInfo = packageInfo;
    }

    public Collection<CreateCardRequest> getWhiteCards() {
        return whiteCards;
    }

    public void setWhiteCards(Collection<CreateCardRequest> whiteCards) {
        this.whiteCards = whiteCards;
    }

    public Collection<CreateCardRequest> getBlackCards() {
        return blackCards;
    }

    public void setBlackCards(Collection<CreateCardRequest> blackCards) {
        this.blackCards = blackCards;
    }
}
