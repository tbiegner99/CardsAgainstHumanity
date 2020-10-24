package com.tj.cardsagainsthumanity.models.gameplay;

import com.tj.cardsagainsthumanity.models.AuditedEntity;
import com.tj.cardsagainsthumanity.models.cards.WhiteCard;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "card_play")
public class CardPlay extends AuditedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "card_play_id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "game_round_id")
    private GameRound round;
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.EAGER)
    @JoinTable(name = "card_play_white_cards",
            joinColumns = @JoinColumn(name = "card_play_id"),
            inverseJoinColumns = @JoinColumn(name = "white_card_id")
    )
    private List<WhiteCard> cards;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Player player;
    @Column(name = "winner")
    private Boolean isWinner;
    @Column(name = "revealed")
    private boolean isRevealed;

    public CardPlay() {
        cards = new ArrayList<>();
    }

    public CardPlay(GameRound round, List<WhiteCard> cards) {
        this();
        setRound(round);
        setCards(cards);
        setWinner(false);
    }

    public CardPlay(GameRound round, Player player, List<WhiteCard> card) {
        this(round, card);
        setPlayer(player);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public GameRound getRound() {
        return round;
    }

    public void setRound(GameRound round) {
        this.round = round;
    }

    public List<WhiteCard> getCards() {
        return cards;
    }

    public void setCards(List<WhiteCard> cards) {
        this.cards = cards;
    }

    public void addCard(WhiteCard card) {
        this.cards.add(card);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Boolean getWinner() {
        return isWinner;
    }

    public void setWinner(Boolean winner) {
        isWinner = winner;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    @Override
    public boolean equals(Object o) {
        CardPlay cardPlay = (CardPlay) o;
        return Objects.equals(getRound(), cardPlay.getRound()) &&
                Objects.equals(getPlayer(), cardPlay.getPlayer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRound(), getPlayer());
    }
}
