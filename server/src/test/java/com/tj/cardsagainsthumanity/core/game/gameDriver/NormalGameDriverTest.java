package com.tj.cardsagainsthumanity.core.game.gameDriver;

import com.tj.cardsagainsthumanity.core.game.RoundDriver;
import com.tj.cardsagainsthumanity.core.game.events.EventFactory;
import com.tj.cardsagainsthumanity.core.game.events.dispatcher.GameEventManager;
import com.tj.cardsagainsthumanity.core.game.events.handler.*;
import com.tj.cardsagainsthumanity.core.game.events.types.GameEvent;
import com.tj.cardsagainsthumanity.core.game.events.types.PlayerEvent;
import com.tj.cardsagainsthumanity.core.game.events.types.RoundEvent;
import com.tj.cardsagainsthumanity.core.game.factory.impl.RoundDriverFactory;
import com.tj.cardsagainsthumanity.core.game.handManager.DrawStrategy;
import com.tj.cardsagainsthumanity.core.game.handManager.HandManager;
import com.tj.cardsagainsthumanity.dao.CardDao;
import com.tj.cardsagainsthumanity.dao.gameplay.GameDriverDao;
import com.tj.cardsagainsthumanity.models.cards.WhiteCard;
import com.tj.cardsagainsthumanity.models.gameplay.CardPlay;
import com.tj.cardsagainsthumanity.models.gameplay.Game;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.models.gameplay.game.Scoreboard;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class NormalGameDriverTest {
    @Mock
    private Game mockGame;
    @Mock
    private GameDriverDao gameDao;
    @Mock
    private CardDao cardDao;
    @Mock
    private RoundDriverFactory roundFactory;
    @Mock
    private Player player;
    @Mock
    private Player player2;
    @Mock
    private Scoreboard gameScore;
    @Mock
    private Scoreboard roundScore;

    private NormalGameDriver driver;
    @Mock
    private RoundDriver mockRound;
    @Mock
    private WhiteCard mockHand;
    @Mock
    private EventFactory eventFactory;
    @Mock
    private GameEventManager eventManager;
    @Mock
    private HandManager<WhiteCard> handManager;
    @Mock
    private DrawStrategy<WhiteCard> drawStrategy;
    @Mock
    private Map<Player, Set<WhiteCard>> mockHands;
    @Mock
    private GameEvent gameStartEvent;
    @Mock
    private GameEvent gameOverEvent;
    @Mock
    private GameEvent gameChangeEvent;
    @Mock
    private PlayerEvent playerEvent;
    @Mock
    private RoundEvent roundStartEvent;
    @Mock
    private RoundEvent roundEndEvent;
    @Mock
    private GameStartedEventHandler gameStartHandler;
    @Mock
    private GameOverEventHandler gameOverHandler;
    @Mock
    private GameStateChangeEventHandler gameStateChangeEventHandler;
    @Mock
    private RoundStartedEventHandler roundStartedEventHandler;
    @Mock
    private RoundOverEventHandler roundOverEventHandler;
    @Mock
    private RoundStateChangeEventHandler roundStateChangeEventHandler;
    @Mock
    private PlayerStateChangeHandler playerStateChangeHandler;
    @Mock
    private CardPlay cardPlay1;
    @Mock
    private CardPlay cardPlay2;

    @Mock
    private List<WhiteCard> play1Cards;
    @Mock
    private List<WhiteCard> play2Cards;


    @Before
    public void setUp() throws Exception {
        driver = new NormalGameDriver(mockGame, handManager, drawStrategy, eventFactory, eventManager, gameDao, cardDao, roundFactory);
        when(roundFactory.createGameRound(any(), any(), eq(eventFactory), any(), any())).thenReturn(mockRound);
        when(mockGame.getScoreboard()).thenReturn(gameScore);
        when(mockRound.getScoresForPlayers()).thenReturn(roundScore);
        when(handManager.getAllCardsInHand()).thenReturn(mockHands);
        when(eventFactory.createPlayerCreatedEvent(driver, player)).thenReturn(playerEvent);
        when(eventFactory.createGameStartedEvent(driver)).thenReturn(gameStartEvent);
        when(eventFactory.createGameOverEvent(driver)).thenReturn(gameOverEvent);
        when(eventFactory.createGameChangeEvent(driver)).thenReturn(gameChangeEvent);
        when(eventFactory.createRoundStartedEvent(driver, mockRound)).thenReturn(roundStartEvent);
        when(eventFactory.createRoundOverEvent(driver, mockRound)).thenReturn(roundEndEvent);
        when(mockGame.getId()).thenReturn(11);
        when(mockGame.getState()).thenReturn(Game.GameState.OVER);
        when(mockGame.getCode()).thenReturn("someGameCode");
        when(cardDao.getRandomWhiteCardsForGame(any(), any())).thenReturn(Arrays.asList(mockHand));
        when(cardPlay1.getCards()).thenReturn(play1Cards);
        when(cardPlay2.getCards()).thenReturn(play2Cards);
        when(cardPlay1.getPlayer()).thenReturn(player);
        when(cardPlay2.getPlayer()).thenReturn(player2);
    }

    @Test
    public void start() {
        driver.start();
        verify(mockGame, times(1)).setState(Game.GameState.STARTED);
        //verify(gameDao, times(1)).saveGame(driver);
        verify(eventManager, times(1)).dispatchGameStartedEvent(gameStartEvent);
    }

    @Test
    public void end() {
        driver.end();
        verify(mockGame, times(1)).setState(Game.GameState.OVER);
        // verify(gameDao, times(1)).removeFromCache(driver);
        //verify(gameDao, times(1)).saveGame(driver);
        verify(eventManager, times(1)).dispatchGameOverEvent(gameOverEvent);
    }

    @Test
    public void pause() {
        driver.pause();
        verify(mockGame, times(1)).setState(Game.GameState.PAUSED);
        // verify(gameDao, times(1)).saveGame(driver);
        verify(eventManager, times(1)).dispatchGameChangeEvent(gameChangeEvent);
    }

    @Test
    public void resume() {
        driver.resume();
        verify(mockGame, times(1)).setState(Game.GameState.STARTED);
        //  verify(gameDao, times(1)).saveGame(driver);
        verify(eventManager, times(1)).dispatchGameChangeEvent(gameChangeEvent);
    }

    @Test
    public void save() {
        driver.save();
        //  verify(gameDao, times(1)).saveGame(driver);
    }

    @Test
    public void getGame() {
        Game result = driver.getGame();
        assertSame(result, mockGame);
    }

    @Test
    public void nextRound() {
        RoundDriver result = driver.nextRound();
        //it saves the round
        verify(mockRound, times(1)).save();
        //it sets the current game round
        assertSame(driver.getCurrentRound(), mockRound);
        //it sets round for game
        verify(mockGame, times(1)).setCurrentRound(any());
        //it save the game
        // verify(gameDao, times(1)).saveGame(driver);
        assertSame(result, mockRound);
    }

    @Test
    public void getScore() {
        assertSame(driver.getScore(), gameScore);
    }

    @Test
    public void addPlayer_whenPlayerAlreadyInGame() {
        when(mockGame.hasPlayer(player)).thenReturn(true);
        driver.addPlayer(player);
        verify(handManager, times(0)).fillOutHandForPlayer(any(), any());

    }

    @Test
    public void addPlayer() {
        when(mockGame.hasPlayer(player)).thenReturn(false);
        driver.addPlayer(player);
        verify(handManager, times(1)).fillOutHandForPlayer(player, drawStrategy);
        verify(mockGame, times(1)).setPlayerHands(mockHands);
        //  verify(gameDao, times(1)).saveGame(driver);
        verify(eventManager, times(1)).dispatchPlayerChangeEvent(playerEvent);

    }

    @Test
    public void addDisplay() {
        driver.addDisplay();
    }

    @Test
    public void addAudienceMember() {
        driver.addAudienceMember();
    }

    @Test
    public void removePlayer() {
        driver.removePlayer(player);
        verify(handManager, times(1)).removePlayer(player);
        verify(mockGame, times(1)).removePlayer(player);
        //  verify(gameDao, times(1)).saveGame(driver);
    }

    @Test
    public void getPlayers() {
        when(mockGame.getPlayers()).thenReturn(new LinkedList<>(Arrays.asList(player, player)));
        List<Player> expectedResult = new ArrayList<>(Arrays.asList(player, player));
        assertEquals(driver.getPlayers(), expectedResult);
    }

    @Test
    public void getNumberOfPlayers() {
        when(mockGame.getPlayers()).thenReturn(new LinkedList<>(Arrays.asList(player, player)));
        List<Player> expectedResult = new ArrayList<>(Arrays.asList(player, player));
        assertEquals(driver.getNumberOfPlayers().intValue(), expectedResult.size());
    }

    @Test
    public void onRoundOver() {
        driver.nextRound();
        driver.onRoundOver(driver.getCurrentRound());
        verify(mockRound, times(2)).save();
        verify(gameScore, times(1)).addScores(roundScore);
        verify(roundFactory, times(2)).createGameRound(any(), any(), eq(eventFactory), any(), any());
    }

    @Test
    public void registerGameStartedHandler() {
        driver.registerGameStartedHandler(gameStartHandler);
        verify(eventManager, times(1)).registerGameStartedHandler(gameStartHandler);
    }

    @Test
    public void registerGameOverHandler() {
        driver.registerGameOverHandler(gameOverHandler);
        verify(eventManager, times(1)).registerGameOverHandler(gameOverHandler);
    }

    @Test
    public void registerGameStateChangeHandler() {
        driver.registerGameStateChangeHandler(gameStateChangeEventHandler);
        verify(eventManager, times(1)).registerGameStateChangeHandler(gameStateChangeEventHandler);
    }

    @Test
    public void registerRoundStartedHandler() {
        driver.registerRoundStartedHandler(roundStartedEventHandler);
        verify(eventManager, times(1)).registerRoundStartedHandler(roundStartedEventHandler);
    }

    @Test
    public void registerRoundOverHandler() {
        driver.registerRoundOverHandler(roundOverEventHandler);
        verify(eventManager, times(1)).registerRoundOverHandler(roundOverEventHandler);
    }

    @Test
    public void registerRoundStateChangeHandler() {
        driver.registerRoundStateChangeHandler(roundStateChangeEventHandler);
        verify(eventManager, times(1)).registerRoundStateChangeHandler(roundStateChangeEventHandler);
    }

    @Test
    public void registerPlayerStateChangeEvent() {
        driver.registerPlayerStateChangeEvent(playerStateChangeHandler);
        verify(eventManager, times(1)).registerPlayerStateChangeEvent(playerStateChangeHandler);
    }

    @Test
    public void replacePlayedCards() {
    Collection<CardPlay> cardPlays = Arrays.asList(cardPlay1,cardPlay2);
        driver.replacePlayedCards(cardPlays);
    verify(handManager,times(1)).removeCardsFromHand(player,play1Cards);
        verify(handManager,times(1)).removeCardsFromHand(player2,play2Cards);
        verify(handManager,times(1)).fillOutHandsForAllPlayers(drawStrategy);
    }

    @Test
    public void getGameId() {
        assertEquals(11, driver.getGameId().intValue());
    }

    @Test
    public void getGameCode() {
        assertEquals("someGameCode", driver.getCode());
    }

    @Test
    public void getState() {
        assertEquals(Game.GameState.OVER, driver.getState());
    }

}