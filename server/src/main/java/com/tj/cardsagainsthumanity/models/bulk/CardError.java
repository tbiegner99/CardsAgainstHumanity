package com.tj.cardsagainsthumanity.models.bulk;

public class CardError {
    private String cardText;
    private String errorMessage;
    private Integer status;

    public CardError(String cardText, String errorMessage, Integer status) {
        this.cardText = cardText;
        this.errorMessage = errorMessage;
        this.status = status;
    }

    public String getCardText() {
        return cardText;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Integer getStatus() {
        return status;
    }
}
