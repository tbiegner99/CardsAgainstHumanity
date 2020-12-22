package com.tj.cardsagainsthumanity.serializer.responseModel.deck;

import java.util.List;

public class DetailedDeckResponse extends DeckResponse {

    private List<Integer> whiteCards;
    private List<Integer> blackCards;

    public DetailedDeckResponse() {
    }

    public DetailedDeckResponse(DeckResponse deckResponse) {
        this.setId(deckResponse.getId());
        this.setName((deckResponse.getName()));
        this.setOwner(deckResponse.getOwner());
    }

    public List<Integer> getWhiteCards() {
        return whiteCards;
    }

    public void setWhiteCards(List<Integer> whiteCards) {
        this.whiteCards = whiteCards;
    }

    public List<Integer> getBlackCards() {
        return blackCards;
    }

    public void setBlackCards(List<Integer> blackCards) {
        this.blackCards = blackCards;
    }
}
