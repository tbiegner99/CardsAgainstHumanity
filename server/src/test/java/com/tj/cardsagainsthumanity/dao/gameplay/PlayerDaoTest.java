package com.tj.cardsagainsthumanity.dao.gameplay;

import com.tj.cardsagainsthumanity.models.gameplay.Player;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class PlayerDaoTest {
    @Mock
    private EntityManager em;


    @Mock
    private Player mockQueryResponse;

    @Mock
    private Player testPlayer;

    @Mock
    private Query createdQuery;


    private PlayerDao playerDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        playerDao = new PlayerDao(em);
        when(em.createNativeQuery(any(), eq(Player.class))).thenReturn(createdQuery);
        when(em.merge(testPlayer)).thenReturn(testPlayer);
        when(createdQuery.getSingleResult()).thenReturn(mockQueryResponse);
    }


    @Test
    public void saveCard() {
        Player result = playerDao.savePlayer(testPlayer);
        //it calls the entity manager to persiste the card
        verify(em, times(1)).merge(testPlayer);

        //it returns the expectedresult
        assertSame(result, testPlayer);
    }

    @Test
    public void getPlayerByEmail() {
        String testName = "testEmail";
        Player result = playerDao.getPlayerByEmail(testName);
        //it creates query with name as parameter
        verify(em, times(1)).createNativeQuery(any(), eq(Player.class));
        verify(createdQuery, times(1)).setParameter("email", testName);
        //it returns result of single query
        assertSame(result, mockQueryResponse);
    }


    @Test
    public void getPlayerById() {
        Integer testId = 3;
        when(em.find(Player.class, testId)).thenReturn(mockQueryResponse);
        Player result = playerDao.getPlayerById(testId);
        //it finds player withId
        verify(em, times(1)).find(Player.class, testId);
        //it returns result of single query
        assertSame(result, mockQueryResponse);
    }
}