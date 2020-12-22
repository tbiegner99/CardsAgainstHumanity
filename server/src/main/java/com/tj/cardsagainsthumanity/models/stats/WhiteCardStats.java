package com.tj.cardsagainsthumanity.models.stats;

import com.tj.cardsagainsthumanity.models.cards.WhiteCard;

import javax.persistence.*;

@Entity
@Table(name = "white_card_stats")
public class WhiteCardStats extends CommonCardStats implements CardStats {
    @Id
    @Column(name = "white_card_id")
    private Integer whiteCardId;

    @OneToOne(mappedBy = "stats")
    @JoinColumn(name = "white_card_id")
    @MapsId
    private WhiteCard card;

    private Integer wins;

    public WhiteCardStats() {
        wins = 0;
    }

    public Integer getWhiteCardId() {
        return whiteCardId;
    }

    public void setWhiteCardId(Integer whiteCardId) {
        this.whiteCardId = whiteCardId;
    }

    @Override
    public void incrementWins() {
        wins++;
    }

    @Override
    public Integer getWins() {
        return wins;
    }


    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public WhiteCard getCard() {
        return card;
    }

    public void setCard(WhiteCard card) {
        this.card = card;
    }
}
