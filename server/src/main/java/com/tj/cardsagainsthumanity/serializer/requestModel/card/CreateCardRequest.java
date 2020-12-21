package com.tj.cardsagainsthumanity.serializer.requestModel.card;

import java.util.Objects;

public class CreateCardRequest {
    private Integer packageId;
    private Integer picks;
    private String cardText;


    public CreateCardRequest() {
    }

    public CreateCardRequest(String text, Integer picks, Integer packageId) {
        this.cardText = text;
        this.picks = picks;
        this.packageId = packageId;
    }

    public Integer getPicks() {
        return picks;
    }

    public void setPicks(Integer picks) {
        this.picks = picks;
    }

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public String getCardText() {
        return cardText;
    }

    public void setCardText(String cardText) {
        this.cardText = cardText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CreateCardRequest that = (CreateCardRequest) o;
        return Objects.equals(packageId, that.packageId) &&
                Objects.equals(cardText, that.cardText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(packageId,
                cardText);
    }
}
