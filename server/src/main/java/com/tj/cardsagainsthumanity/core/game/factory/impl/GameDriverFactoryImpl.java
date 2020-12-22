package com.tj.cardsagainsthumanity.core.game.factory.impl;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.core.game.events.EventFactory;
import com.tj.cardsagainsthumanity.core.game.events.dispatcher.GameEventManager;
import com.tj.cardsagainsthumanity.core.game.events.dispatcher.NormalGameEventManager;
import com.tj.cardsagainsthumanity.core.game.factory.GameDriverFactory;
import com.tj.cardsagainsthumanity.core.game.gameDriver.NormalGameDriver;
import com.tj.cardsagainsthumanity.core.game.handManager.DrawStrategy;
import com.tj.cardsagainsthumanity.core.game.handManager.GenericHandManager;
import com.tj.cardsagainsthumanity.core.game.handManager.HandManager;
import com.tj.cardsagainsthumanity.core.game.handManager.drawStrategy.NormalDrawStrategy;
import com.tj.cardsagainsthumanity.dao.CardDao;
import com.tj.cardsagainsthumanity.dao.gameplay.GameDriverDao;
import com.tj.cardsagainsthumanity.models.cards.WhiteCard;
import com.tj.cardsagainsthumanity.models.gameplay.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameDriverFactoryImpl implements GameDriverFactory {
    private final EventFactory eventFactory;
    private GameDriverDao store;
    private CardDao cardDao;
    private RoundDriverFactory roundDriverFactory;

    @Autowired
    public GameDriverFactoryImpl(EventFactory eventFactory, CardDao cardDao, RoundDriverFactory roundDriverFactory) {
        this.cardDao = cardDao;
        this.roundDriverFactory = roundDriverFactory;
        this.eventFactory = eventFactory;
    }

    @Autowired
    public void setStore(GameDriverDao store) {
        this.store = store;
    }

    GameEventManager createGameEventManager() {
        return new NormalGameEventManager();
    }

    DrawStrategy createDrawStrategy(Game game) {
        return new NormalDrawStrategy(cardDao, game);
    }

    NormalGameDriver createNormalGameDriver(Game game, HandManager<WhiteCard> handManager, DrawStrategy drawStrategy, GameEventManager eventManager) {
        return new NormalGameDriver(game, handManager, drawStrategy, eventFactory, eventManager, store, cardDao, roundDriverFactory);
    }

    HandManager<WhiteCard> createHandManager(Game game) {

        return new GenericHandManager<>(game.getPlayerHands(), getMaxCardsInHand(game));
    }

    @Override
    public GameDriver createGameDriver(Game game) {
        HandManager<WhiteCard> handManager = createHandManager(game);
        GameEventManager eventManager = createGameEventManager();
        DrawStrategy drawStrategy = createDrawStrategy(game);
        return createNormalGameDriver(game, handManager, drawStrategy, eventManager);
    }


    private int getMaxCardsInHand(Game game) {
        return 10;
    }
}
