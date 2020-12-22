package com.tj.cardsagainsthumanity.services.gameplay;

import com.tj.cardsagainsthumanity.dao.gameplay.PlayerDao;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class PlayerServiceTest {

    private final String testEmail = "testEmail";
    @Mock
    private PlayerDao dao;
    @Mock
    private Player testPlayer;
    private PlayerService service;

    @Before
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);
        service = new PlayerService(dao);
        when(dao.getPlayerByEmail(any())).thenReturn(testPlayer);
        when(dao.getPlayerById(4)).thenReturn(testPlayer);
        when(dao.savePlayer(testPlayer)).thenReturn(testPlayer);
    }


    @Test
    public void createPlayer() {
        Player result = service.createPlayer(testPlayer);
        //it invokes dao
        verify(dao, times(1)).savePlayer(testPlayer);
        //it returns dao returned value
        assertSame(result, testPlayer);
    }

    @Test
    public void getPlayerId() {
        Player result = service.getPlayerById(4);
        //it invokes dao
        verify(dao, times(1)).getPlayerById(4);
        //it returns dao returned value
        assertSame(result, testPlayer);
    }

    @Test
    public void getPlayerByEmail() {
        Player result = service.getPlayerByEmail(testEmail);
        //it invokes dao
        verify(dao, times(1)).getPlayerByEmail(testEmail);
        //it returns dao returned value
        assertSame(result, testPlayer);
    }
}