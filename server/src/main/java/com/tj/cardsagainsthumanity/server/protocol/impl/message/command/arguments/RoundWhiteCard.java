package com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments;

import com.tj.cardsagainsthumanity.models.cards.Card;

public class RoundWhiteCard {
    private String text;
    private Integer id;

    public RoundWhiteCard() {
    }

    public RoundWhiteCard(Integer id, String text) {
        this();
        setId(id);
        setText(text);
    }

    public static RoundWhiteCard fromCard(Card card) {
        return new RoundWhiteCard(card.getId(), card.getText());
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
