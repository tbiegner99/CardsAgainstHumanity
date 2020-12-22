package com.tj.cardsagainsthumanity.models.cards;

import com.tj.cardsagainsthumanity.models.stats.BlackCardStats;
import com.tj.cardsagainsthumanity.models.stats.CardStats;

import javax.persistence.*;

@Entity
@Table(name = "black_card")
public class BlackCard extends CommonCard implements Card {

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, optional = false)
    @PrimaryKeyJoinColumn
    private BlackCardStats stats;

    @Column(name = "answers")
    private Integer numberOfAnswers;

    public BlackCard() {
        setStats(new BlackCardStats());
        stats.setCard(this);
    }

    public BlackCard(CardPackage cardPackage, String cardText) {
        super(cardPackage, cardText);
        setStats(new BlackCardStats());
        stats.setCard(this);
    }

    @Override
    public CardStats getPlayStats() {
        return stats;
        // return null;
    }

    public void setStats(BlackCardStats stats) {
        this.stats = stats;
    }

    public Integer getNumberOfAnswers() {
        return numberOfAnswers;
    }

    @Override
    public void onPreUpdate() {
        this.updateBlanks();
    }

    @Override
    public void onPrePersist() {
        this.updateBlanks();
    }

    private void updateBlanks() {
        String text = getText().replaceAll("_+", "_");
        int numberOfBlanks = text.split("_").length - 1;
        this.numberOfAnswers = Math.max(numberOfBlanks, 1);
    }
}
