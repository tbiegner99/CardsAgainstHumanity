package com.tj.cardsagainsthumanity.serializer.responseModel.cardPackage;

import java.util.Objects;

public class CardResponse {
    private Integer id;
    private PackageResponse cardPackage;
    private String cardText;

    public CardResponse(Integer id, String cardText, PackageResponse pack) {
        this.id = id;
        this.cardPackage = pack;
        this.cardText = cardText;
    }

    public Integer getId() {
        return id;
    }

    public PackageResponse getCardPackage() {
        return cardPackage;
    }

    public String getCardText() {
        return cardText;
    }

    @Override
    public boolean equals(Object o) {
        CardResponse that = (CardResponse) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getCardPackage(), that.getCardPackage()) &&
                Objects.equals(getCardText(), that.getCardText());
    }
}
