package com.tj.cardsagainsthumanity.models.gameStatus;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.core.game.RoundDriver;
import com.tj.cardsagainsthumanity.models.gameplay.Player;

import java.util.Optional;

public class PlayerInfo {
    private Integer playerId;
    private String displayName;
    private String name;
    private Integer score;
    private Integer czarIndex;
    private boolean isCurrentCzar;

    public PlayerInfo(Integer playerId, String displayName, String name, Integer score, Integer czarIndex, boolean isCurrentCzar) {
        this.playerId = playerId;
        this.displayName = displayName;
        this.name = name;
        this.score = score;
        this.czarIndex = czarIndex;
        this.isCurrentCzar = isCurrentCzar;
    }


    public static PlayerInfo fromPlayer(GameDriver game, Player player) {
        Integer playerId = player.getId();
        Optional<RoundDriver> round = Optional.ofNullable(game.getCurrentRound());
        boolean isCurrentCzar = round.isPresent() && round.get().getRound().getCzarId().equals(playerId);
        return new PlayerInfo(
                playerId,
                player.getDisplayName(),
                player.getFullName(),
                game.getScore().getScoreForPlayer(playerId).getScore(),
                game.getCzarPositionFor(playerId),
                isCurrentCzar
        );
    }


    public Integer getPlayerId() {
        return playerId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getName() {
        return name;
    }

    public Integer getScore() {
        return score;
    }

    public Integer getCzarIndex() {
        return czarIndex;
    }

    public boolean isCurrentCzar() {
        return isCurrentCzar;
    }


}
