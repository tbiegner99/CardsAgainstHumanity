package com.tj.cardsagainsthumanity.models.gameplay.game;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static junit.framework.TestCase.assertEquals;

public class ScoreboardTest {

    @Test
    public void addScores() {
        PlayerScore player1Score = new PlayerScore(1, 1, 2);
        PlayerScore player2Score = new PlayerScore(2, 1, 6);
        Scoreboard existing = new Scoreboard();
        existing.setScores(new HashSet<>(Arrays.asList(player1Score)));

        Scoreboard toAdd = new Scoreboard();
        toAdd.setScores(new HashSet<>(Arrays.asList(player1Score, player2Score)));

        existing.addScores(toAdd);

        assertEquals(existing.getScoreForPlayer(1).getScore(), 4);
        assertEquals(existing.getScoreForPlayer(2).getScore(), 6);
    }

    private void createPlayerScore(Integer id, float score) {

    }
}