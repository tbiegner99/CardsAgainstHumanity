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
    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "package_id", nullable = false)
    private CardPackage cardPackage;
    @Column(name = "card_text")
    private String cardText;


    public CommonCard() {
    }

    public CommonCard(CardPackage cardPackage, String cardText) {
        setCardPackage(cardPackage);
        setText(cardText);
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
    public CardPackage getCardPackage() {
        return cardPackage;
    }

    @Override
    public void setCardPackage(CardPackage cardPackage) {
        this.cardPackage = cardPackage;
    }

    @Override
    public String getText() {
        return cardText;
    }

    @Override
    public void setText(String cardText) {
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
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(cardText, that.cardText);
    }
}
