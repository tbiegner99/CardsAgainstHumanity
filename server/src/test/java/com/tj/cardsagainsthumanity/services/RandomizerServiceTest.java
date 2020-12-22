package com.tj.cardsagainsthumanity.services;

import com.tj.cardsagainsthumanity.dao.CardDao;
import com.tj.cardsagainsthumanity.models.cards.BlackCard;
import com.tj.cardsagainsthumanity.models.cards.Card;
import com.tj.cardsagainsthumanity.models.cards.WhiteCard;
import com.tj.cardsagainsthumanity.models.gameplay.GameRound;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class RandomizerServiceTest {

    @Mock
    private CardDao dao;

    @Mock
    private WhiteCard expectedWhiteResult;

    @Mock
    private WhiteCard expectedWhiteResult2;

    @Mock
    private BlackCard expectedBlackResult;

    @Mock
    private Player czar;

    private RandomizerService service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        service = new RandomizerService(dao);
        when(expectedWhiteResult.getText()).thenReturn("card1");
        when(expectedWhiteResult2.getText()).thenReturn("card2");
        when(dao.getRandomWhiteCards(eq(2))).thenReturn(Arrays.asList(expectedWhiteResult, expectedWhiteResult2));
        when(dao.getRandomWhiteCards(eq(4))).thenReturn(Arrays.asList(expectedWhiteResult, expectedWhiteResult2, expectedWhiteResult, expectedWhiteResult2));
        when(dao.getRandomWhiteCards(eq(1))).thenReturn(Arrays.asList(expectedWhiteResult));
        when(expectedBlackResult.getNumberOfAnswers()).thenReturn(1);
        when(dao.getRandomBlackCard()).thenReturn(expectedBlackResult);
    }

    @Test
    public void getRandomBlackCard() {
        Card result = service.getRandomBlackCard();

        //it calls dao
        verify(dao, times(1)).getRandomBlackCard();
        //it returns dao result
        assertSame(result, expectedBlackResult);
    }

    @Test
    public void getRandomWhiteCard() {
        Card result = service.getRandomWhiteCard();

        //it calls dao
        verify(dao, times(1)).getRandomWhiteCards(1);
        //it returns dao result
        assertSame(result, expectedWhiteResult);
    }

    @Test
    public void getRandomRoundForNumberOfPlayers() {
        GameRound result = service.getRandomRoundForNumberOfPlayers(czar, 2);

        //it calls dao to get black card
        verify(dao, times(1)).getRandomBlackCard();
        //it calls dao to get white cards for all the players
        verify(dao, times(1)).getRandomWhiteCards(2);
        //it creates a game round
        assertSame(result.getCzar(), czar);
        assertSame(result.getBlackCard(), expectedBlackResult);
        assertSame(result.getWinner(), null);
        assertEquals(2,result.getPlays().size());
    }


    @Test
    public void getRandomRoundForNumberOfPlayers_forMultipleAnswerCard() {
        when(expectedBlackResult.getNumberOfAnswers()).thenReturn(2);
        GameRound result = service.getRandomRoundForNumberOfPlayers(czar, 2);

        //it calls dao to get black card
        verify(dao, times(1)).getRandomBlackCard();
        //it calls dao to get white cards for all the players
        verify(dao, times(1)).getRandomWhiteCards(4);

        //it creates a game round
        assertSame(result.getCzar(), czar);
        assertSame(result.getBlackCard(), expectedBlackResult);
        assertSame(result.getWinner(), null);
        assertEquals(result.getPlays().size(), 2);
    }
}