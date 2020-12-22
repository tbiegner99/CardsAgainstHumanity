package com.tj.cardsagainsthumanity.core.game.roundDriver;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.core.game.events.EventFactory;
import com.tj.cardsagainsthumanity.core.game.events.dispatcher.GameEventDispatcher;
import com.tj.cardsagainsthumanity.dao.gameplay.GameRoundDao;
import com.tj.cardsagainsthumanity.models.cards.WhiteCard;
import com.tj.cardsagainsthumanity.models.gameplay.CardPlay;
import com.tj.cardsagainsthumanity.models.gameplay.Game;
import com.tj.cardsagainsthumanity.models.gameplay.GameRound;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.models.gameplay.game.Scoreboard;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class NormalGameRoundDriverTest {
    @Mock
    private GameRound round;
    @Mock
    private GameDriver game;
    @Mock
    private Game mockGame;
    @Mock
    private CardPlay winningPlay;
    @Mock
    private Player player;
    @Mock
    private GameRoundDao roundDao;
    @Mock
    private GameEventDispatcher eventDispatcher;
    @Mock
    private EventFactory eventFactory;

    @Mock
    private List<WhiteCard> cards;


    private NormalGameRoundDriver driver;

    @Before
    public void setUp() throws Exception {
        driver = new NormalGameRoundDriver(game, eventDispatcher, eventFactory, round, roundDao);
        when(round.getWinner()).thenReturn(winningPlay);
        when(winningPlay.getPlayer()).thenReturn(player);
        when(game.getGame()).thenReturn(mockGame);
    }


    @Test
    public void save() {
        driver.save();
        verify(roundDao, times(1)).saveGameRound(round);
    }

    @Test
    public void getScoresForPlayers() {
        Scoreboard expectedResult = new Scoreboard();
        expectedResult.addScore(mockGame, player, 1);
        Scoreboard result = driver.getScoresForPlayers();
        assertEquals(result, expectedResult);

    }

    @Test
    public void getScoresForPlayers_whenNoWinner() {
        when(round.getWinner()).thenReturn(null);
        Scoreboard result = driver.getScoresForPlayers();
        assertEquals(result, new Scoreboard());

    }

    @Test
    public void playCards() {
        driver.playCards(winningPlay);
        verify(round, times(1)).addCardPlay(winningPlay);
    }

    @Test
    public void playCards_withPlayerAndCards() {
        driver.playCards(player, cards);
        verify(round, times(1)).addCardPlay(new CardPlay(round, player, cards));
    }

    @Test
    public void declareWinner() {
        when(player.isCzarFor(round)).thenReturn(true);
        driver.declareWinner(player, winningPlay);
        verify(round, times(1)).setWinner(winningPlay);
        verify(game, times(1)).onRoundOver(driver);
    }

    @Test
    public void declareWinnerByPlayId() {
        Integer playId = 3;
        when(player.isCzarFor(round)).thenReturn(true);
        when(round.getPlayById(playId)).thenReturn(winningPlay);
        driver.declareWinner(player, playId);
        verify(round, times(1)).getPlayById(playId);
        verify(round, times(1)).setWinner(winningPlay);
        verify(game, times(1)).onRoundOver(driver);
    }

    @Test
    public void declareWinner_whenDeclarerIsNotCzar() {
        when(player.isCzarFor(round)).thenReturn(false);
        try {
            driver.declareWinner(player, winningPlay);
        } catch (IllegalArgumentException e) {
            return;
        }
        fail("Expected exception");
    }

    @Test
    public void endRound() {
        driver.endRound();
    }

    @Test
    public void endRound_whenNoWinner() {
        when(round.getWinner()).thenReturn(null);
        try {
            driver.endRound();
        } catch (IllegalStateException e) {
            return;
        }
        fail("Expected exception");
    }

    @Test
    public void areAllCardsIn_allValidPlayersHaveMadePlays_returnsTrue() {
        when(round.getPlays()).thenReturn(new HashSet<>(Arrays.asList(new CardPlay(), new CardPlay(round, cards))));
        when(game.getNumberOfPlayers()).thenReturn(3);
        assertTrue(driver.areAllCardsIn());
    }

    @Test
    public void areAllCardsIn_whenAtLeastOnePlayerHasNotPlayed_returnsFalse() {
        when(round.getPlays()).thenReturn(new HashSet<>(Arrays.asList(new CardPlay())));
        when(game.getNumberOfPlayers()).thenReturn(3);
        assertFalse(driver.areAllCardsIn());
    }

    @Test
    public void upvote() {
        driver.upvote(player, winningPlay);
    }

    @Test
    public void downvote() {
        driver.downvote(player, winningPlay);
    }

    @Test
    public void isRoundOver_returnsTrue_whenThereIsAWinner() {
        when(round.getWinner()).thenReturn(winningPlay);
        assertTrue(driver.isRoundOver());
    }

    @Test
    public void isRoundOver_returnsFalse_whenThereIsNoWinner() {
        when(round.getWinner()).thenReturn(null);
        assertFalse(driver.isRoundOver());
    }
}