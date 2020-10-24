package com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.body.PlayerResponseBody;

import java.util.Set;
import java.util.stream.Collectors;

public class ExtendedGameStatus extends GameStatus {

    private Set<PlayerResponseBody> players;

    public ExtendedGameStatus() {

    }

    protected ExtendedGameStatus(GameStatus status, Set<PlayerResponseBody> players) {
        super(status.getGameId(), status.getState(), status.getRound(), status.getCurrentHand());
        this.players = players;
    }

    public static ExtendedGameStatus fromGame(GameDriver driver, Player currentPlayer) {
        GameStatus baseStatus = GameStatus.fromGame(driver, currentPlayer);
        Set<PlayerResponseBody> players = driver.getPlayers().stream().map(player -> new PlayerResponseBody(player.getId(), player.getDisplayName(), player.getFirstName(), player.getLastName())).collect(Collectors.toSet());
        return new ExtendedGameStatus(baseStatus, players);
    }

    public Set<PlayerResponseBody> getPlayers() {
        return players;
    }

    public void setPlayers(Set<PlayerResponseBody> players) {
        this.players = players;
    }
}
