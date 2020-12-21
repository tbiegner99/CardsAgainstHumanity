package com.tj.cardsagainsthumanity.models.gameplay;

import com.tj.cardsagainsthumanity.models.AuditedEntity;
import com.tj.cardsagainsthumanity.models.cards.DeckInfo;
import com.tj.cardsagainsthumanity.models.cards.WhiteCard;
import com.tj.cardsagainsthumanity.models.gameplay.game.PlayerHandCard;
import com.tj.cardsagainsthumanity.models.gameplay.game.Scoreboard;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "game")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Game extends AuditedEntity {

    @Id
    @GeneratedValue
    @Column(name = "game_id")
    private Integer id;
    @Embedded
    private Scoreboard scoreboard;
    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private GameState state = GameState.INITIALIZING;
    @OneToOne
    @JoinColumn(name = "deck_id", referencedColumnName = "deck_id")
    private DeckInfo deck;
    private String code;
    @OneToOne()
    @JoinColumn(name = "current_round_id", referencedColumnName = "game_round_id")
    private GameRound currentRound;
    @Transient
    private Map<Player, Set<WhiteCard>> playerHands;
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "game")
    private Set<PlayerHandCard> cardsInPlay;

    public Game() {
        playerHands = new HashMap<>();
        scoreboard = new Scoreboard();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public void setScoreboard(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public GameRound getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(GameRound currentRound) {
        this.currentRound = currentRound;
    }

    public DeckInfo getDeck() {
        return deck;
    }

    public void setDeck(DeckInfo deck) {
        this.deck = deck;
    }

    private void setCardsInPlay() {
        Set<PlayerHandCard> ret = new HashSet<>();

        playerHands.entrySet()
                .forEach(entry -> {
                    Set<PlayerHandCard> cards = entry.getValue().stream()
                            .map(card -> new PlayerHandCard(this, entry.getKey(), card))
                            .collect(Collectors.toSet());
                    ret.addAll(cards);
                });
        this.cardsInPlay = ret;
    }

    public Set<PlayerHandCard> getCardsInPlay() {
        return cardsInPlay;
    }

    public void setCardsInPlay(Set<PlayerHandCard> cards) {
        cardsInPlay = cards;
    }

    public Map<Player, Set<WhiteCard>> getPlayerHands() {
        return playerHands;
    }

    public void setPlayerHands(Map<Player, Set<WhiteCard>> playerHands) {
        this.playerHands = playerHands;

    }

    @PostLoad
    private void initalizePlayersAfterLoad() {

        Map<Player, Set<WhiteCard>> handCards = cardsInPlay.stream()
                .collect(
                        Collectors.groupingBy(
                                PlayerHandCard::getPlayer,
                                Collectors.mapping(card -> card.getWhiteCard(), Collectors.toSet()
                                )));
        setPlayerHands(handCards);
    }

    public boolean hasPlayer(Player player) {
        return playerHands.containsKey(player);
    }

    public Collection<Player> getPlayers() {
        return playerHands.keySet();
    }

    public void removePlayer(Player player) {
        playerHands.remove(player);
    }

    public void addPlayerToScoreboard(Player player) {
        scoreboard.addScore(this.getId(), player.getId(), 0);
    }

    @Transient
    public int getNumberOfPlayers() {
        return getPlayers().size();
    }

    public static enum GameState {
        INITIALIZING,
        STARTED,
        PAUSED,
        OVER
    }

}
