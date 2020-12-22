package com.tj.cardsagainsthumanity.models.cards;

import java.util.List;

public class DeckCards extends DeckInfo {

    private List<Integer> whiteCards;
    private List<Integer> blackCards;


    public DeckCards() {
    }

    public DeckCards(DeckInfo baseInfo, List<Integer> whiteCards, List<Integer> blackCards) {
        this.setName(baseInfo.getName());
        this.setDeckId(baseInfo.getDeckId());
        this.setCreated(baseInfo.getCreated());
        this.setUpdated(baseInfo.getUpdated());
        this.setPlayer(baseInfo.getPlayer());
        this.setWhiteCardCount(baseInfo.getWhiteCardCount());
        this.setBlackCardCount(baseInfo.getBlackCardCount());
        this.whiteCards = whiteCards;
        this.blackCards = blackCards;
    }

    public List<Integer> getWhiteCards() {
        return whiteCards;
    }

    public List<Integer> getBlackCards() {
        return blackCards;
    }
}
