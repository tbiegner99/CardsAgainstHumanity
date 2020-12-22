package com.tj.cardsagainsthumanity.services.gameplay;

import com.tj.cardsagainsthumanity.dao.gameplay.CardPlayDao;
import com.tj.cardsagainsthumanity.dao.gameplay.GameRoundDao;
import com.tj.cardsagainsthumanity.exceptions.NoRowUpdatedException;
import com.tj.cardsagainsthumanity.models.cards.BlackCard;
import com.tj.cardsagainsthumanity.models.cards.WhiteCard;
import com.tj.cardsagainsthumanity.models.gameplay.CardPlay;
import com.tj.cardsagainsthumanity.models.gameplay.GameRound;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GameRoundServiceTest {
    @Mock
    private CardPlayDao cardPlayDao;
    @Mock
    private GameRoundDao gameRoundDao;
    @Mock
    private GameRound testRound;
    @Mock
    private BlackCard blackCard;
    @Mock
    private GameRound savedRound;
    @Mock
    private Player player;
    @Mock
    private WhiteCard card;

    private GameRoundService gameRoundService;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        gameRoundService = new GameRoundService(gameRoundDao, cardPlayDao);

        when(gameRoundDao.getGameRound(6)).thenReturn(testRound);
        when(gameRoundDao.saveGameRound(testRound)).thenReturn(savedRound);
        when(gameRoundDao.getGameRound(6)).thenReturn(testRound);
        when(blackCard.getNumberOfAnswers()).thenReturn(3);
        when(testRound.getBlackCard()).thenReturn(blackCard);
        when(player.isCzarFor(any())).thenReturn(true);
    }

    @Test
    public void playCardsForRound() {
        List<WhiteCard> cards = Arrays.asList(card, card, card);
        GameRound result = gameRoundService.playCardsForRound(6, player, cards);
        //it loads the game round for id
        verify(gameRoundDao, times(1)).getGameRound(6);
        //it creates play and adds it to round
        CardPlay play = new CardPlay(testRound, player, cards);
        verify(testRound, times(1)).addCardPlay(play);
        //it saves cardPlay
        verify(cardPlayDao, times(1)).saveCardPlay(play);
        //it saves game round
        verify(gameRoundDao, times(1)).saveGameRound(testRound);
        //it returns saved game round
        assertSame(result, savedRound);
    }

    @Test
    public void playCardsForRound_throwsIllegalOperationExceptionWhenInvalidNumberOfCardsProvided() {
        List<WhiteCard> cards = Arrays.asList(card, card);
        try {
            gameRoundService.playCardsForRound(6, player, cards);
        } catch (IllegalArgumentException e) {
            return;
        }
        fail("expected exception");

    }

    @Test
    public void saveGameRound() {
        GameRound result = gameRoundService.saveGameRound(testRound);
        //it invokes dao
        verify(gameRoundDao, times(1)).saveGameRound(testRound);
        //it returns dao result
        assertSame(result, savedRound);
    }

    @Test
    public void getGameRound() {
        GameRound result = gameRoundService.getGameRound(6);
        verify(gameRoundDao, times(1)).getGameRound(6);
        assertSame(result, testRound);
    }

    @Test
    public void chooseWinnerForGameRound() {
        GameRound result = gameRoundService.chooseWinnerForGameRound(player, 6, 3);
        //it sets the winning card play on the game round
        verify(gameRoundDao, times(1)).setWinningPlayForRound(6, 3);
        //it sets the card play as a winner
        verify(cardPlayDao, times(1)).makeCardPlayWinner(3);
        //it returns the game round
        assertSame(result, testRound);
    }

    @Test
    public void chooseWinnerForGameRound_whenPlayerIsNotCzarForRound() {
        when(player.isCzarFor(any())).thenReturn(false);
        try {
            gameRoundService.chooseWinnerForGameRound(player, 6, 3);
        } catch (IllegalArgumentException ex) {
            return;
        }
        fail("Expected exception");
    }

    @Test
    public void chooseWinnerForGameRound_whenNoRowsUpdatedInDao() {
        doThrow(new NoRowUpdatedException("msg")).when(gameRoundDao).setWinningPlayForRound(6, 3);
        try {
            gameRoundService.chooseWinnerForGameRound(player, 6, 3);
        } catch (IllegalArgumentException e) {
            return;
        }
        fail("Expected Exception");

    }
}