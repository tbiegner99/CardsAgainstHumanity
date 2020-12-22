package com.tj.cardsagainsthumanity.dao;

import com.tj.cardsagainsthumanity.models.cards.BlackCard;
import com.tj.cardsagainsthumanity.models.cards.Card;
import com.tj.cardsagainsthumanity.models.cards.WhiteCard;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class CardDaoTest {

    @Mock
    private EntityManager em;

    @Mock
    private Card expectedResult;

    @Mock
    private Card testCard;

    @Mock
    private WhiteCard expectedWhite;

    @Mock
    private BlackCard expectedBlack;

    @Mock
    private Query randomWhiteQuery;

    @Mock
    private Query randomBlackQuery;

    private CardDao cardDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        cardDao = new CardDao(em);
        when(em.merge(any())).thenReturn(expectedResult);
        when(em.find(WhiteCard.class, 4)).thenReturn(expectedWhite);
        when(em.find(BlackCard.class, 5)).thenReturn(expectedBlack);
        when(em.createNativeQuery(anyString(), eq(WhiteCard.class))).thenReturn(randomWhiteQuery);
        when(randomWhiteQuery.getResultList()).thenReturn(Arrays.asList(expectedWhite, expectedWhite));
        when(em.createNativeQuery(anyString(), eq(BlackCard.class))).thenReturn(randomBlackQuery);
        when(randomBlackQuery.getSingleResult()).thenReturn(expectedBlack);
    }

    @Test
    public void saveCard() {
        Card result = cardDao.saveCard(testCard);
        //it calls the entity manager to persiste the card
        verify(em, times(1)).merge(testCard);

        //it returns the expectedresult
        assertSame(result, expectedResult);
    }

    @Test
    public void deleteCard() {
        Card result = cardDao.deleteCard(testCard);
        // it sets the soft delete flag on the object
        verify(testCard, times(1)).setDeleted(true);
        //it saves the object;
        verify(em, times(1)).merge(testCard);
        //it returns the result of the save
        assertSame(result, expectedResult);
    }

    @Test
    public void getWhiteCard() {
        Card result = cardDao.getWhiteCard(4);
        //it fetches from the entity manager;
        verify(em, times(1)).find(WhiteCard.class, 4);
        //returns the result￼
        assertSame(result, expectedWhite);

    }

    @Test
    public void getRandomWhiteCards() {
        List<WhiteCard> result = cardDao.getRandomWhiteCardsForGame(7, 2);
        //it fetches from the entity manager;
        verify(em, times(1)).createNativeQuery(any(), eq(WhiteCard.class));
        //it sets limit to number to return;
        verify(randomWhiteQuery, times(1)).setParameter("limit", 2);
        verify(randomWhiteQuery, times(1)).setParameter("gameId", 7);
        //returns the result
        assertEquals(result, Arrays.asList(expectedWhite, expectedWhite));
    }

    @Test
    public void getRandomWhiteCardsForGame() {
        List<WhiteCard> result = cardDao.getRandomWhiteCards(2);
        //it fetches from the entity manager;
        verify(em, times(1)).createNativeQuery("SELECT * FROM white_card ORDER BY RAND() LIMIT :limit", WhiteCard.class);
        verify(randomWhiteQuery, times(1)).setParameter("limit", 2);
        //returns the result
        assertEquals(result, Arrays.asList(expectedWhite, expectedWhite));
    }

    @Test
    public void getBlackCard() {
        Card result = cardDao.getBlackCard(5);
        //it fetches from the entity manager;
        verify(em, times(1)).find(BlackCard.class, 5);
        //returns the result
        assertSame(result, expectedBlack);
    }

    @Test
    public void getRandomBlackCard() {
        BlackCard result = cardDao.getRandomBlackCard();
        //it fetches from the entity manager;
        verify(em, times(1)).createNativeQuery("SELECT * FROM black_card ORDER BY RAND() LIMIT :limit", BlackCard.class);
        verify(randomBlackQuery, times(1)).setParameter("limit", 1);
        //returns the result
        assertSame(result, expectedBlack);

    }

    @Test
    public void getRandomBlackCardForGame() {
        BlackCard result = cardDao.getRandomBlackCardForGame(2);
        //it fetches from the entity manager;
        verify(em, times(1)).createNativeQuery(any(), eq(BlackCard.class));
        verify(randomBlackQuery, times(1)).setParameter("limit", 1);
        verify(randomBlackQuery, times(1)).setParameter("gameId", 2);
        //returns the result
        assertEquals(result, expectedBlack);
    }
}