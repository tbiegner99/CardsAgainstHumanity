package com.tj.cardsagainsthumanity.dao.gameplay;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.core.game.factory.GameDriverFactory;
import com.tj.cardsagainsthumanity.models.gameplay.Game;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GameDriverDaoTest {

    private GameDriverDao driverDao;
    @Mock
    private GameDriver gameDriver1;
    @Mock
    private GameDriver gameDriver2;
    @Mock
    private GameDriver loadedDriver;
    @Mock
    private Game loadedGame;
    @Mock
    private GameDriverFactory driverFactory;
    @Mock
    private GameDao gameDao;

    @Before
    public void setUp() throws Exception {
        driverDao = new GameDriverDao(driverFactory, gameDao);
        when(gameDriver1.getGameId()).thenReturn(1);
        when(gameDriver2.getGameId()).thenReturn(2);
        when(loadedDriver.getGameId()).thenReturn(3);
        when(gameDriver1.getCode()).thenReturn("code1");
        when(gameDriver2.getCode()).thenReturn("code2");
        when(loadedDriver.getCode()).thenReturn("code3");
        when(loadedDriver.getGame()).thenReturn(loadedGame);
        when(driverFactory.createGameDriver(loadedGame)).thenReturn(loadedDriver);
        when(gameDao.getGameByCode(any())).thenReturn(loadedGame);
        when(gameDao.getGame(any())).thenReturn(loadedGame);
    }

    @Test
    public void clearGameCache() {
        driverDao.addGameDriverToCache(gameDriver1);
        driverDao.addGameDriverToCache(gameDriver2);
        assertEquals(driverDao.getCacheSize(), 2);
        driverDao.clearGameCache();
        assertEquals(driverDao.getCacheSize(), 0);

    }

    @Test
    public void getGame_whenInCache() {
        driverDao.addGameDriverToCache(gameDriver1);
        driverDao.addGameDriverToCache(gameDriver2);
        GameDriver driver = driverDao.getGame(1);
        assertSame(driver, gameDriver1);
        verify(gameDao, times(0)).getGame(any());
    }

    @Test
    public void getGame_whenNotInCache() {
        GameDriver driver = driverDao.getGame(3);
        assertSame(driver, loadedDriver);
        verify(gameDao, times(1)).getGame(any());
        assertTrue(driverDao.isInCache("code3"));
        assertTrue(driverDao.isInCache(3));
    }

    @Test
    public void getGameByCode_whenInCache() {
        driverDao.addGameDriverToCache(gameDriver1);
        driverDao.addGameDriverToCache(gameDriver2);
        GameDriver driver = driverDao.getGameByCode("code1");
        assertSame(driver, gameDriver1);
        verify(gameDao, times(0)).getGameByCode(any());
    }

    @Test
    public void getGameByCode_whenNotInCache() {
        GameDriver driver = driverDao.getGameByCode("code1");
        assertSame(driver, loadedDriver);
        verify(gameDao, times(1)).getGameByCode(any());
        assertTrue(driverDao.isInCache("code3"));
        assertTrue(driverDao.isInCache(3));
    }


    @Test
    public void loadGameDriverByCode() {
        GameDriver driver = driverDao.getGameByCode("code1");
        assertSame(driver, loadedDriver);
        verify(gameDao, times(1)).getGameByCode(any());
        assertTrue(driverDao.isInCache("code3"));
        assertTrue(driverDao.isInCache(3));
    }

    @Test
    public void loadGameDriver() {
        GameDriver driver = driverDao.getGame(3);
        assertSame(driver, loadedDriver);
        verify(gameDao, times(1)).getGame(any());
        assertTrue(driverDao.isInCache("code3"));
        assertTrue(driverDao.isInCache(3));
    }


    @Test
    public void saveGame() {
        driverDao.saveGame(loadedDriver);
        verify(gameDao, times(1)).saveGame(loadedGame);
        assertTrue(driverDao.isInCache("code3"));
        assertTrue(driverDao.isInCache(3));
    }

    @Test
    public void remove() {
        driverDao.addGameDriverToCache(gameDriver1);
        driverDao.addGameDriverToCache(gameDriver2);
        assertTrue(driverDao.isInCache(1));
        assertTrue(driverDao.isInCache(2));
        assertTrue(driverDao.isInCache("code1"));
        assertTrue(driverDao.isInCache("code2"));
        driverDao.remove(gameDriver2);
        assertTrue(driverDao.isInCache(1));
        assertFalse(driverDao.isInCache(2));
        assertTrue(driverDao.isInCache("code1"));
        assertFalse(driverDao.isInCache("code2"));
    }

    @Test
    public void isInCache() {
        driverDao.addGameDriverToCache(gameDriver1);
        driverDao.addGameDriverToCache(gameDriver2);
        assertTrue(driverDao.isInCache(1));
        assertTrue(driverDao.isInCache(2));
        assertTrue(driverDao.isInCache("code1"));
        assertTrue(driverDao.isInCache("code2"));
        driverDao.clearGameCache();
        assertFalse(driverDao.isInCache(1));
        assertFalse(driverDao.isInCache(2));
        assertFalse(driverDao.isInCache("code1"));
        assertFalse(driverDao.isInCache("code2"));
    }
}