package com.tj.cardsagainsthumanity.models.cards;

import com.tj.cardsagainsthumanity.models.stats.CardStats;
import com.tj.cardsagainsthumanity.models.stats.WhiteCardStats;

import javax.persistence.*;

@Entity
@Table(name = "white_card")
public class WhiteCard extends CommonCard {

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, optional = false)
    @PrimaryKeyJoinColumn
    private WhiteCardStats stats;

    public WhiteCard() {
        setStats(new WhiteCardStats());
        stats.setCard(this);
    }


    public WhiteCard(CardPackage cardPackage, String cardText) {
        super(cardPackage, cardText);
        setStats(new WhiteCardStats());
        stats.setCard(this);
    }

    @Override
    public CardStats getPlayStats() {
        return stats;
    }

    public void setStats(WhiteCardStats stats) {
        this.stats = stats;
    }

}
