package com.tj.cardsagainsthumanity.dao.gameplay;

import com.tj.cardsagainsthumanity.exceptions.NoRowUpdatedException;
import com.tj.cardsagainsthumanity.models.gameplay.GameRound;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GameRoundDaoTest {
    @Mock
    private GameRound testRound;
    @Mock
    private GameRound resultRound;
    @Mock
    private EntityManager entityManager;
    @Mock
    private Query query;

    private GameRoundDao dao;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        dao = new GameRoundDao(entityManager);
        when(entityManager.merge(testRound)).thenReturn(resultRound);
        when(entityManager.createNativeQuery(any())).thenReturn(query);
        when(entityManager.find(GameRound.class, 4)).thenReturn(resultRound);
        when(query.executeUpdate()).thenReturn(1);
    }

    @Test
    public void saveGameRound() {
        GameRound result = dao.saveGameRound(testRound);
        //it saves the object using the entity manager
        verify(entityManager, times(1)).merge(testRound);
        //it returns the result of the merge
        assertSame(result, resultRound);

    }

    @Test
    public void saveGameRound_whenRoundIdIsNull_persistsRound() {
        when(testRound.getId()).thenReturn(null);
        GameRound result = dao.saveGameRound(testRound);
        //it saves the object using the entity manager
        verify(entityManager, times(1)).persist(testRound);
        //it returns the result of the merge
        assertSame(result, testRound);

    }


    @Test
    public void getGameRound() {
        GameRound result = dao.getGameRound(4);
        verify(entityManager, times(1)).find(GameRound.class, 4);
        assertSame(result, resultRound);
    }

    @Test
    public void setWinningPlayForRound() {
        dao.setWinningPlayForRound(11, 8);
        verify(query, times(1)).setParameter("roundId", 11);
        verify(query, times(1)).setParameter("playId", 8);
        verify(query, times(1)).executeUpdate();
    }

    @Test
    public void setWinningPlayForRound_throwsExceptionWhenNoRowsUpdated() {
        when(query.executeUpdate()).thenReturn(0);
        try {
            dao.setWinningPlayForRound(11, 8);
        } catch (NoRowUpdatedException ex) {
            return;
        }
        fail("Expected exception");
    }
}