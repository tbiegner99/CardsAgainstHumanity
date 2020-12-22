package com.tj.cardsagainsthumanity.core.game.factory;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.models.gameplay.Game;

public interface GameDriverFactory {
    GameDriver createGameDriver(Game game);
}
