package com.tj.cardsagainsthumanity.server.socket;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.core.game.events.handler.GameJoinedHandler;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.server.protocol.message.CommandContext;

import java.util.Optional;

public class ConnectionContext implements CommandContext {
    private Optional<Player> player;
    private Optional<GameDriver> game;
    private Optional<GameJoinedHandler> gameJoinHandler;

    public ConnectionContext() {
        player = Optional.empty();
        game = Optional.empty();
        gameJoinHandler = Optional.empty();
    }

    public ConnectionContext(GameJoinedHandler gameHandler) {
        this();
        gameJoinHandler = Optional.of(gameHandler);
    }

    @Override
    public Optional<Player> getPlayer() {
        return this.player;
    }

    @Override
    public Optional<GameDriver> getCurrentGame() {
        return game;
    }

    @Override
    public void joinGame(GameDriver driver) {
        game = Optional.of(driver);
        gameJoinHandler.ifPresent(handler -> handler.onGameJoined(driver));
    }

    @Override
    public void login(Player player) {
        this.player = Optional.ofNullable(player);
    }

    @Override
    public void logout() {
        this.player = Optional.empty();
    }

    public Optional<GameJoinedHandler> getCurrentJoinHandler() {
        return gameJoinHandler;
    }

    public void setGameHandler(GameJoinedHandler gameJoinHandler) {
        this.gameJoinHandler = Optional.ofNullable(gameJoinHandler);
    }
}
