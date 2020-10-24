package com.tj.cardsagainsthumanity.services.gameplay;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.dao.gameplay.GameDriverDao;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.models.gameplay.game.Scoreboard;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceTest {
    private final String mockCode = "mockCode";
    private GameService service;

    @Mock
    private GameDriverDao gameDao;
    @Mock
    private GameDriver mockDriver;
    @Mock
    private Scoreboard mockScore;
    @Mock
    private Player mockPlayer;

    @Before
    public void setup() {
        service = new GameService(gameDao);
        when(gameDao.getGame(7)).thenReturn(mockDriver);
        when(gameDao.getGameByCode(mockCode)).thenReturn(mockDriver);
        when(gameDao.createGameDriver(any())).thenReturn(mockDriver);
        when(mockDriver.getScore()).thenReturn(mockScore);
    }

    @Test
    public void newGame() {
        GameDriver result = service.newGame();
        verify(gameDao, times(1)).saveGame(mockDriver);
        assertSame(result, mockDriver);
    }

    @Test
    public void startGame() {
        GameDriver result = service.startGame(7);
        verify(mockDriver, times(1)).start();
        assertSame(result, mockDriver);

    }

    @Test
    public void joinGame() {
        GameDriver result = service.joinGame(mockPlayer, mockCode);
        verify(mockDriver, times(1)).addPlayer(mockPlayer);
        assertSame(result, mockDriver);
    }

    @Test
    public void getScore() {
        Scoreboard result = service.getScore(7);
        assertSame(result, mockScore);
    }
}