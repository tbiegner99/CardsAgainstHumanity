package com.tj.cardsagainsthumanity.models.gameplay.game;

import com.tj.cardsagainsthumanity.models.gameplay.Game;
import com.tj.cardsagainsthumanity.models.gameplay.Player;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Embeddable
public class Scoreboard {

    @MapKey(name = "playerId")
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true, mappedBy = "game")
    private Map<Integer, PlayerScore> scores;

    public Scoreboard() {
        scores = new HashMap<>();
    }


    public Map<Integer, PlayerScore> getScores() {
        return this.scores;
    }


    public void setScores(Set<PlayerScore> scores) {
        Map<Integer, PlayerScore> scoresMap = scores.stream().collect(Collectors.toMap(
                score -> score.getPlayerId(),
                score -> score
        ));
        setScores(scoresMap);
    }

    public void setScores(Map<Integer, PlayerScore> scores) {
        this.scores = scores;
    }

    public void addScores(Scoreboard scoreboard) {
        scoreboard.getScores().values().stream().forEach(this::combineScores);
    }

    private void combineScores(PlayerScore scoreToAdd) {
        PlayerScore existingScore = getScoreForPlayer(scoreToAdd.getPlayerId());
        if (existingScore != null) {
            existingScore.add(scoreToAdd);
        } else {
            addScore(scoreToAdd);
        }

    }

    public PlayerScore getScoreForPlayer(Integer playerId) {
        return scores.get(playerId);
    }

    public void addScore(PlayerScore winnerScore) {
        scores.put(winnerScore.getPlayerId(), winnerScore);
    }

    public void addScore(Integer gameId, Integer player, int score) {
        addScore(new PlayerScore(player, gameId, score));
    }

    public void addScore(Game game, Player player, int score) {
        addScore(game.getId(), player.getId(), score);
    }

    @Override
    public String toString() {
        return "Scoreboard{" +
                "scores=" + scores +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        Scoreboard that = (Scoreboard) o;
        return allScoresSame(that);
    }

    private boolean allScoresSame(Scoreboard board) {
        return scores.keySet()
                .stream()
                .map(player -> this.playerScoreSame(player, board))
                .reduce(true, (acc, val) -> acc && val);
    }

    private boolean playerScoreSame(Integer playerId, Scoreboard obj) {
        PlayerScore otherScore = obj.getScoreForPlayer(playerId);
        PlayerScore thisScore = getScoreForPlayer(playerId);
        return otherScore != null && thisScore.equals(otherScore);
    }
}


