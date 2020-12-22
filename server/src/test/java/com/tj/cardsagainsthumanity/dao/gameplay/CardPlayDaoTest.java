package com.tj.cardsagainsthumanity.dao.gameplay;

import com.tj.cardsagainsthumanity.exceptions.NoRowUpdatedException;
import com.tj.cardsagainsthumanity.models.gameplay.CardPlay;
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
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CardPlayDaoTest {
    @Mock
    private CardPlay testPlay;
    @Mock
    private CardPlay resultPlay;
    @Mock
    private EntityManager entityManager;
    @Mock
    private Query query;

    private CardPlayDao dao;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        dao = new CardPlayDao(entityManager);
        when(entityManager.merge(testPlay)).thenReturn(resultPlay);
        when(entityManager.createNativeQuery(any())).thenReturn(query);
        when(query.executeUpdate()).thenReturn(1);
    }

    @Test
    public void saveCardPlay() {
        CardPlay result = dao.saveCardPlay(testPlay);
        //it saves the object using the entity manager
        verify(entityManager, times(1)).merge(testPlay);
        //it returns the result of the merge
        assertSame(result, resultPlay);

    }

    @Test
    public void makeCardPlayWinner() {
        dao.makeCardPlayWinner(6);
        verify(query, times(1)).setParameter("playId", 6);
        verify(query, times(1)).executeUpdate();
    }

    @Test
    public void makeCardPlayWinner_whenNoUpdatePerformed() {
        when(query.executeUpdate()).thenReturn(0);
        try {
            dao.makeCardPlayWinner(6);
        } catch (NoRowUpdatedException ex) {
            return;
        }
        fail("expected exception");
    }

}