package com.tj.cardsagainsthumanity.models.cards;

import javax.persistence.*;

@Entity
@Table(name = "deck_entry")
public class DeckEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entry_id")
    private Integer id;
    @Column(name = "deck_id")
    private Integer deckId;
    @Column(name = "package_id")
    private Integer packageId;
    @Column(name = "card_id")
    private Integer cardId;
    @Column(name = "card_type")
    private String cardType;
    private boolean exclude;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeckId() {
        return deckId;
    }

    public void setDeckId(Integer deckId) {
        this.deckId = deckId;
    }

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public CardType getCardTypeEnum() {
        if (cardType.equals("white")) {
            return CardType.WHITE;
        }
        return CardType.BLACK;
    }

    public boolean isExclude() {
        return exclude;
    }

    public void setExclude(boolean exclude) {
        this.exclude = exclude;
    }
}
