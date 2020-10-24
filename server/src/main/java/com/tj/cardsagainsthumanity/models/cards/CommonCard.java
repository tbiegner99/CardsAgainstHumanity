package com.tj.cardsagainsthumanity.models.cards;

import com.tj.cardsagainsthumanity.models.AuditedEntity;

import javax.persistence.*;
import java.util.Objects;

@MappedSuperclass
public abstract class CommonCard extends AuditedEntity implements Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "package_id")
    private CardPackage cardPackage;
    @Column(name = "card_text")
    private String cardText;


    public CommonCard() {
    }

    public CommonCard(CardPackage cardPackage, String cardText) {
        setPackage(cardPackage);
        setCardText(cardText);
    }

    @Override
    public int hashCode() {
        return cardText.hashCode();
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public CardPackage getPackage() {
        return cardPackage;
    }

    @Override
    public void setPackage(CardPackage cardPackage) {
        this.cardPackage = cardPackage;
    }

    @Override
    public String getText() {
        return cardText;
    }

    public void setCardText(String cardText) {
        this.cardText = cardText;
    }

    @Override
    public void incrementLikes() {
        getPlayStats().incrementLikes();
    }

    @Override
    public void incrementWins() {
        this.getPlayStats().incrementWins();
    }

    @Override
    public boolean equals(Object o) {
        CommonCard that = (CommonCard) o;
        return Objects.equals(cardPackage, that.cardPackage) &&
                Objects.equals(cardText, that.cardText);
    }
}
