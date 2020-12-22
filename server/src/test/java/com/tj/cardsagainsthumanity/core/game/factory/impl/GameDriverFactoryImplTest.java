package com.tj.cardsagainsthumanity.core.game.factory.impl;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.core.game.events.EventFactory;
import com.tj.cardsagainsthumanity.core.game.events.dispatcher.GameEventManager;
import com.tj.cardsagainsthumanity.core.game.events.dispatcher.NormalGameEventManager;
import com.tj.cardsagainsthumanity.core.game.gameDriver.NormalGameDriver;
import com.tj.cardsagainsthumanity.core.game.handManager.DrawStrategy;
import com.tj.cardsagainsthumanity.core.game.handManager.GenericHandManager;
import com.tj.cardsagainsthumanity.core.game.handManager.HandManager;
import com.tj.cardsagainsthumanity.dao.CardDao;
import com.tj.cardsagainsthumanity.dao.gameplay.GameDriverDao;
import com.tj.cardsagainsthumanity.models.cards.WhiteCard;
import com.tj.cardsagainsthumanity.models.gameplay.Game;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GameDriverFactoryImplTest {
    private GameDriverFactoryImpl driverFactory;
    @Mock
    private EventFactory eventFactory;
    @Mock
    private GameDriverDao store;
    @Mock
    private CardDao cardDao;
    @Mock
    private RoundDriverFactory roundFactory;
    @Mock
    private Game game;
    @Mock
    private HandManager<WhiteCard> handManager;
    @Mock
    private GameEventManager eventManager;
    @Mock
    private DrawStrategy<WhiteCard> drawStrategy;
    @Mock
    private NormalGameDriver mockDriver;


    @Before
    public void setUp() throws Exception {
        driverFactory = Mockito.spy(new GameDriverFactoryImpl(eventFactory, cardDao, roundFactory));
        driverFactory.setStore(store);
    }

    @Test
    public void createNormalGameDriver() {
        GameDriver driver = driverFactory.createNormalGameDriver(game, handManager, drawStrategy, eventManager);
        assertTrue(driver instanceof NormalGameDriver);
    }

    @Test
    public void createHandManager() {
        HandManager<WhiteCard> driver = driverFactory.createHandManager(game);
        verify(game, times(1)).getPlayerHands();
        assertTrue(driver instanceof GenericHandManager);
        assertEquals(driver.getMaxCardsInHand(), 10);
        assertEquals(driver.getMinCardsInHand(), 10);
    }

    @Test
    public void createGameDriver() {
        when(driverFactory.createDrawStrategy(game)).thenReturn(drawStrategy);
        when(driverFactory.createGameEventManager()).thenReturn(eventManager);
        doReturn(handManager).when(driverFactory).createHandManager(game);
        doReturn(mockDriver).when(driverFactory).createNormalGameDriver(game, handManager, drawStrategy, eventManager);
        GameDriver driver = driverFactory.createGameDriver(game);
        verify(driverFactory, times(1)).createNormalGameDriver(game, handManager, drawStrategy, eventManager);
        assertSame(driver, mockDriver);
    }

    @Test
    public void createGameEventManager() {
        GameEventManager evtManager = driverFactory.createGameEventManager();
        assertTrue(evtManager instanceof NormalGameEventManager);
    }
}