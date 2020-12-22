package com.tj.cardsagainsthumanity.server.protocol.message;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.models.gameplay.Player;

import java.util.Optional;

public interface CommandContext {

    Optional<Player> getPlayer();

    Optional<GameDriver> getCurrentGame();

    void joinGame(GameDriver driver);

    void login(Player player);

    void logout();
}
