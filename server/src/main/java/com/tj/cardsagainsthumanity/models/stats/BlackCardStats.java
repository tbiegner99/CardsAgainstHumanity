package com.tj.cardsagainsthumanity.models.stats;

import com.tj.cardsagainsthumanity.models.cards.BlackCard;

import javax.persistence.*;

@Entity
@Table(name = "black_card_stats")
public class BlackCardStats extends CommonCardStats {

    @Id
    @Column(name = "black_card_id")
    private Integer blackCardId;

    @OneToOne(mappedBy = "stats")
    @JoinColumn(name = "black_card_id")
    @MapsId()
    private BlackCard card;

    @Transient
    @Override
    public Integer getWins() {
        return 0;
    }

    @Override
    public void incrementWins() {

    }

    public Integer getBlackCardId() {
        return blackCardId;
    }

    public void setBlackCardId(Integer blackCardId) {
        this.blackCardId = blackCardId;
    }

    public BlackCard getCard() {
        return card;
    }

    public void setCard(BlackCard card) {
        this.card = card;
    }
}
