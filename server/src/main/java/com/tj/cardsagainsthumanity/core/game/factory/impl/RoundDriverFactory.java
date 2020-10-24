package com.tj.cardsagainsthumanity.core.game.factory.impl;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.core.game.RoundDriver;
import com.tj.cardsagainsthumanity.core.game.RoundType;
import com.tj.cardsagainsthumanity.core.game.events.EventFactory;
import com.tj.cardsagainsthumanity.core.game.events.dispatcher.GameEventDispatcher;
import com.tj.cardsagainsthumanity.core.game.gameDriver.CzarGenerator;
import com.tj.cardsagainsthumanity.core.game.roundDriver.NormalGameRoundDriver;
import com.tj.cardsagainsthumanity.dao.CardDao;
import com.tj.cardsagainsthumanity.dao.gameplay.GameRoundDao;
import com.tj.cardsagainsthumanity.models.cards.BlackCard;
import com.tj.cardsagainsthumanity.models.gameplay.Game;
import com.tj.cardsagainsthumanity.models.gameplay.GameRound;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoundDriverFactory {
    private CardDao cardDao;

    private GameRoundDao roundDao;

    @Autowired
    public RoundDriverFactory(CardDao cardDao, GameRoundDao roundDao) {
        this.cardDao = cardDao;
        this.roundDao = roundDao;
    }

    public RoundDriver createGameRound(GameDriver gameDriver, GameEventDispatcher eventDispatcher, EventFactory eventFactory, RoundType type, CzarGenerator czarGenerator) {
        Player czar = czarGenerator.next();
        Game game = gameDriver.getGame();
        BlackCard card = cardDao.getRandomBlackCardForGame(game.getId());
        GameRound gameRound = new GameRound(czar, card);
        gameRound.setGame(gameDriver.getGame());
        return new NormalGameRoundDriver(gameDriver, eventDispatcher, eventFactory, gameRound, roundDao);
    }

}
