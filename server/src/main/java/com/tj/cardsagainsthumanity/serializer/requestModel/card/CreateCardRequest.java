package com.tj.cardsagainsthumanity.serializer.requestModel.card;

public class CreateCardRequest {
    private Integer packageId;
    private String cardText;

    public CreateCardRequest(String cardText) {
        this.cardText = cardText;
    }

    public CreateCardRequest() {
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

}
