package com.tj.cardsagainsthumanity.services;

import com.tj.cardsagainsthumanity.dao.CardDao;
import com.tj.cardsagainsthumanity.models.cards.Card;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class CardServiceTest {

    @Mock
    private CardDao dao;
    @Mock
    private Card testCard1;

    @Mock
    private Card testCard2;

    @Mock
    private CardPackageService cardPackageService;

    private CardService service;

    @Before
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);
        service = new CardService(cardPackageService, dao);
        when(dao.deleteCard(any(Card.class))).thenReturn(testCard1);
        when(dao.saveCard(any(Card.class))).thenReturn(testCard1);
    }

    @Test
    public void deleteCard() {
        Card result = service.deleteCard(testCard1);
        //it invokes dao
        verify(dao, times(1)).deleteCard(testCard1);
        //it returns dao returned value
        assertSame(result, testCard1);
    }

    @Test
    public void createCard() {
        Card result = service.createCard(1, testCard1);
        //it invokes dao
        verify(dao, times(1)).saveCard(testCard1);
        //it returns dao returned value
        assertSame(result, testCard1);
    }

    @Test
    public void createCards() {
        Collection<Card> result = service.createCards(1, Arrays.asList(testCard1, testCard2));
        //it invokes dao for each card
        verify(dao, times(1)).saveCard(testCard1);
        verify(dao, times(1)).saveCard(testCard1);

        //it returns collection of saced cards
        assertEquals(result.size(), 2);
        Iterator<Card> it = result.iterator();
        assertSame(it.next(), testCard1);
        assertSame(it.next(), testCard1);
    }
}