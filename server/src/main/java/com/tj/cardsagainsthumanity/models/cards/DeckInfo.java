package com.tj.cardsagainsthumanity.models.cards;

import com.tj.cardsagainsthumanity.models.gameplay.Player;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "deck")
public class DeckInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deck_id")
    private Integer deckId;
    private String name;
    @Column(name = "white_card_count")
    private Integer whiteCardCount;
    @Column(name = "black_card_count")
    private Integer blackCardCount;
    private Date created;
    private Date updated;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Player player;

    public Integer getDeckId() {
        return deckId;
    }

    public void setDeckId(Integer deckId) {
        this.deckId = deckId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date update) {
        this.updated = updated;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Integer getWhiteCardCount() {
        return whiteCardCount;
    }

    public void setWhiteCardCount(Integer whiteCardCount) {
        this.whiteCardCount = whiteCardCount;
    }

    public Integer getBlackCardCount() {
        return blackCardCount;
    }

    public void setBlackCardCount(Integer blackCardCount) {
        this.blackCardCount = blackCardCount;
    }
}
