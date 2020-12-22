package com.tj.cardsagainsthumanity.dao.gameplay;

import com.tj.cardsagainsthumanity.core.cache.BasicCache;
import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.core.game.factory.GameDriverFactory;
import com.tj.cardsagainsthumanity.models.gameplay.Game;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

@Ignore
public class GameDaoTest {
    @Mock
    Game mockGame;
    @Mock
    Game dbGame;
    @Mock
    GameDriver mockDriver;
    @Mock
    EntityManager em;
    @Mock
    GameDriverFactory factory;
    @Mock
    BasicCache<Integer, GameDriver> idCache;
    @Mock
    BasicCache<String, GameDriver> codeCache;
    @Mock
    GameDriver dbDriver;
    @Mock
    GameDriver cacheDriver;
    @Mock
    Query mockQuery;

    GameDao dao;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        dao = new GameDao(em);
        when(mockDriver.getGame()).thenReturn(mockGame);
        when(em.find(eq(Game.class), anyInt())).thenReturn(dbGame);
        when(factory.createGameDriver(mockGame)).thenReturn(mockDriver);
        when(factory.createGameDriver(dbGame)).thenReturn(dbDriver);
        when(mockGame.getId()).thenReturn(5);
        when(mockGame.getCode()).thenReturn("mockCode");
        when(em.createNativeQuery(any(), eq(Game.class))).thenReturn(mockQuery);
        when(mockQuery.getSingleResult()).thenReturn(dbGame);
    }

    @Test
    public void saveGame_whenGameIdIsNotNull_performsMerge() {
        dao.saveGame(mockGame);
        verify(em, times(1)).merge(mockGame);
        verify(em, times(1)).flush();
    }

    @Test
    public void saveGame_whenGameIdIsNull_performsInsert() {

        when(mockGame.getId()).thenReturn(null);
        dao.saveGame(mockGame);
        verify(em, times(1)).persist(mockGame);
        verify(em, times(1)).flush();

    }


    @Test
    public void getGameByCode() {

        Game result = dao.getGameByCode("code");
        verify(mockQuery, times(1)).setParameter("code", "code");
        assertSame(result, dbGame);
    }

    @Test
    public void deleteGame() {
        dao.deleteGame(mockGame);
        verify(mockGame, times(1)).setDeleted(true);
        verify(em, times(1)).merge(mockGame);
        verify(em, times(1)).flush();
    }

    @Test
    public void getGame() {
        Game result = dao.getGame(6);
        verify(em, times(1)).find(Game.class, 6);
        assertSame(result, dbGame);
    }
}
