package com.tj.cardsagainsthumanity.models.gameplay.game;

import com.tj.cardsagainsthumanity.models.cards.WhiteCard;
import com.tj.cardsagainsthumanity.models.gameplay.Game;
import com.tj.cardsagainsthumanity.models.gameplay.Player;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "player_hand")
@IdClass(PlayerHandCard.PlayerHandId.class)
public class PlayerHandCard {
    @Id
    @Column(name = "game_id")
    private Integer gameId;
    @Id
    @Column(name = "player_id")
    private Integer playerId;

    @Id
    @Column(name = "white_card_id")
    private Integer whiteCardId;

    @MapsId
    @ManyToOne
    @JoinColumn(name = "white_card_id")
    private WhiteCard whiteCard;

    @MapsId
    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @MapsId
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;


    public PlayerHandCard() {
    }

    public PlayerHandCard(Game game, Player player, WhiteCard whiteCard) {
        setWhiteCard(whiteCard);
        setGame(game);
        setPlayer(player);
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public Integer getWhiteCardId() {
        return whiteCardId;
    }

    public void setWhiteCardId(Integer whiteCardId) {
        this.whiteCardId = whiteCardId;
    }

    public WhiteCard getWhiteCard() {
        return whiteCard;
    }

    public void setWhiteCard(WhiteCard whiteCard) {
        this.whiteCard = whiteCard;
        setWhiteCardId(whiteCard.getId());
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
        setPlayerId(player.getId());
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
        setGameId(game.getId());
    }

    static class PlayerHandId implements Serializable {
        public Integer gameId;
        public Integer playerId;
        public Integer whiteCardId;
    }
}
