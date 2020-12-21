package com.tj.cardsagainsthumanity.models.gameplay.game;

import com.tj.cardsagainsthumanity.models.gameplay.Game;
import com.tj.cardsagainsthumanity.models.gameplay.Player;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "game_score")
@IdClass(PlayerScore.ScoreId.class)
public class PlayerScore {

    @Id
    @Column(name = "game_id")
    private Integer gameId;
    @Id
    @Column(name = "player_id")
    private Integer playerId;
    @MapsId
    @ManyToOne()
    @JoinColumn(name = "game_id", insertable = false, updatable = false)
    private Game game;
    @MapsId
    @ManyToOne
    @JoinColumn(name = "player_id", insertable = false, updatable = false)
    private Player player;
    private int score = 0;

    public PlayerScore() {
    }


    public PlayerScore(Integer playerId, Integer gameId, int score) {
        this();
        setPlayerId(playerId);
        setGameId(gameId);
        setScore(score);
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    private boolean canBeAdded(PlayerScore score) {
        return score.getPlayerId().equals(playerId) && score.getGameId().equals(gameId);
    }

    public void add(PlayerScore score) {
        if (!canBeAdded(score)) {
            throw new IllegalArgumentException("Scores can not be added");
        }
        add(score.getScore());
    }

    public void add(int score) {
        this.setScore(this.score + score);
    }

    @Override
    public boolean equals(Object o) {
        PlayerScore that = (PlayerScore) o;
        return that.getScore() == getScore() &&
                Objects.equals(getPlayerId(), that.getPlayerId()) &&
                Objects.equals(getGameId(), that.getGameId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlayerId(), getGameId());
    }


    public static class ScoreId implements Serializable {
        public Integer playerId;
        public Integer gameId;
    }
}
