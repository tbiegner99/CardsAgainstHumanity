package com.tj.cardsagainsthumanity.models.gameplay;

import com.tj.cardsagainsthumanity.models.AuditedEntity;
import com.tj.cardsagainsthumanity.models.cards.BlackCard;
import com.tj.cardsagainsthumanity.models.cards.WhiteCard;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "game_round")
public class GameRound extends AuditedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "game_round_id")
    private Integer id;
    @ManyToOne()
    @JoinColumn(name = "black_card_id")
    private BlackCard blackCard;
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;
    @ManyToOne()
    @JoinColumn(name = "czar", referencedColumnName = "user_id")
    private Player czar;
    @Column(name = "czar", insertable = false, updatable = false)
    private Integer czarId;

    @OneToMany(mappedBy = "round", cascade = CascadeType.MERGE)
    private Set<CardPlay> plays;
    @ManyToOne()
    @JoinColumn(name = "winning_play_id")
    private CardPlay winner;

    public GameRound() {
        setPlays(new HashSet<>());

    }

    public GameRound(Player czar, BlackCard blackCard) {
        this();
        setCzar(czar);
        setBlackCard(blackCard);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BlackCard getBlackCard() {
        return blackCard;
    }

    public void setBlackCard(BlackCard blackCard) {
        this.blackCard = blackCard;
    }

    public Set<CardPlay> getPlays() {
        return plays;
    }

    public void setPlays(Set<CardPlay> plays) {
        this.plays = plays;
    }

    public Player getCzar() {
        return czar;
    }

    public void setCzar(Player czar) {
        this.czar = czar;
        this.czarId = czar.getId();
    }

    public CardPlay getWinner() {
        return winner;
    }

    public void setWinner(CardPlay winner) {
        winner.setWinner(true);
        this.winner = winner;
    }

    public Integer getCzarId() {
        return czarId;
    }

    public void setCzarId(Integer czarId) {
        this.czarId = czarId;
    }

    public CardPlay playCardsAnonomously(List<WhiteCard> cards) {
        return playWhiteCards(Player.ANONYMOUS, cards);
    }

    public void addCardPlay(CardPlay play) {
        plays.remove(play);
        plays.add(play);
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public CardPlay playWhiteCards(Player player, List<WhiteCard> cards) {
        CardPlay play = new CardPlay(this, player, cards);
        addCardPlay(play);
        return play;
    }

    @Override
    public boolean equals(Object o) {
        GameRound gameRound = (GameRound) o;
        return Objects.equals(getBlackCard(), gameRound.getBlackCard()) &&
                Objects.equals(game, gameRound.game) &&
                Objects.equals(getCzar(), gameRound.getCzar()) &&
                Objects.equals(getCzarId(), gameRound.getCzarId()) &&
                Objects.equals(getPlays(), gameRound.getPlays()) &&
                Objects.equals(getWinner(), gameRound.getWinner());
    }

    public CardPlay getPlayById(Integer playId) throws NoSuchElementException {
        return getPlays().stream()
                .filter(play -> play.getId() == playId)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No card play with id: " + playId));
    }
}
