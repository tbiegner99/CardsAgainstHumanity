package com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments;

import com.tj.cardsagainsthumanity.models.gameplay.CardPlay;

import java.util.List;
import java.util.stream.Collectors;

public class RoundCardPlay {
    protected Integer id;
    protected List<RoundWhiteCard> cards;

    public RoundCardPlay() {
    }

    public static RoundCardPlay fromCardPlay(CardPlay cardPlay) {
        RoundCardPlay ret = new RoundCardPlay();
        ret.id = cardPlay.getId();
        ret.cards = cardPlay.getCards().stream().map(card -> new RoundWhiteCard(card.getId(), card.getText())).collect(Collectors.toList());

        return ret;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<RoundWhiteCard> getCards() {
        return cards;
    }

    public void setCards(List<RoundWhiteCard> cards) {
        this.cards = cards;
    }
}
