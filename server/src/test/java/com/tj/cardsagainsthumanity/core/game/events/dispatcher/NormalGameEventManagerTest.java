package com.tj.cardsagainsthumanity.core.game.events.dispatcher;

import com.tj.cardsagainsthumanity.core.game.events.handler.*;
import com.tj.cardsagainsthumanity.core.game.events.types.GameEvent;
import com.tj.cardsagainsthumanity.core.game.events.types.PlayerEvent;
import com.tj.cardsagainsthumanity.core.game.events.types.RoundEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class NormalGameEventManagerTest {

    @Mock
    private GameEvent gameEvent;
    @Mock
    private RoundEvent roundEvent;
    @Mock
    private PlayerEvent playerEvent;

    @Mock
    private GameStartedEventHandler gameStartedEventHandler1;
    @Mock
    private GameStartedEventHandler gameStartedEventHandler2;
    @Mock
    private GameOverEventHandler gameOverEventHandler1;
    @Mock
    private GameOverEventHandler gameOverEventHandler2;

    @Mock
    private GameStateChangeEventHandler gameStateChangeEventHandler1;
    @Mock
    private GameStateChangeEventHandler gameStateChangeEventHandler2;

    @Mock
    private RoundOverEventHandler roundOverEventHandler1;
    @Mock
    private RoundOverEventHandler roundOverEventHandler2;

    @Mock
    private RoundStartedEventHandler roundStartedEventHandler1;
    @Mock
    private RoundStartedEventHandler roundStartedEventHandler2;

    @Mock
    private RoundStateChangeEventHandler roundStateChangeEventHandler1;
    @Mock
    private RoundStateChangeEventHandler roundStateChangeEventHandler2;

    @Mock
    private PlayerStateChangeHandler playerStateChangeHandler1;
    @Mock
    private PlayerStateChangeHandler playerStateChangeHandler2;

    private NormalGameEventManager eventManager;

    @Before
    public void setUp() throws Exception {
        eventManager = new NormalGameEventManager();
    }

    @Test
    public void dispatchGameStartedEvent() {
        eventManager.registerGameStartedHandler(gameStartedEventHandler1);
        eventManager.dispatchGameStartedEvent(gameEvent);

        eventManager.registerGameStartedHandler(gameStartedEventHandler2);
        eventManager.dispatchGameStartedEvent(gameEvent);

        verify(gameStartedEventHandler1, times(2)).onGameStarted(gameEvent);
        verify(gameStartedEventHandler2, times(1)).onGameStarted(gameEvent);
    }

    @Test
    public void dispatchGameOverEvent() {
        eventManager.registerGameOverHandler(gameOverEventHandler1);
        eventManager.dispatchGameOverEvent(gameEvent);

        eventManager.registerGameOverHandler(gameOverEventHandler2);
        eventManager.dispatchGameOverEvent(gameEvent);

        verify(gameOverEventHandler1, times(2)).onGameOver(gameEvent);
        verify(gameOverEventHandler2, times(1)).onGameOver(gameEvent);
    }

    @Test
    public void dispatchGameChangeEvent() {
        eventManager.registerGameStateChangeHandler(gameStateChangeEventHandler1);
        eventManager.dispatchGameChangeEvent(gameEvent);

        eventManager.registerGameStateChangeHandler(gameStateChangeEventHandler2);
        eventManager.dispatchGameChangeEvent(gameEvent);

        verify(gameStateChangeEventHandler1, times(2)).onGameStateChange(gameEvent);
        verify(gameStateChangeEventHandler2, times(1)).onGameStateChange(gameEvent);
    }

    @Test
    public void dispatchRoundStartedEvent() {
        eventManager.registerRoundStartedHandler(roundStartedEventHandler1);
        eventManager.dispatchRoundStartedEvent(roundEvent);

        eventManager.registerRoundStartedHandler(roundStartedEventHandler2);
        eventManager.dispatchRoundStartedEvent(roundEvent);

        verify(roundStartedEventHandler1, times(2)).onRoundStart(roundEvent);
        verify(roundStartedEventHandler2, times(1)).onRoundStart(roundEvent);
    }

    @Test
    public void dispatchRoundOverEvent() {
        eventManager.registerRoundOverHandler(roundOverEventHandler1);
        eventManager.dispatchRoundOverEvent(roundEvent);

        eventManager.registerRoundOverHandler(roundOverEventHandler2);
        eventManager.dispatchRoundOverEvent(roundEvent);

        verify(roundOverEventHandler1, times(2)).onRoundOver(roundEvent);
        verify(roundOverEventHandler2, times(1)).onRoundOver(roundEvent);
    }

    @Test
    public void dispatchRoundChangeEvent() {
        eventManager.registerRoundStateChangeHandler(roundStateChangeEventHandler1);
        eventManager.dispatchRoundChangeEvent(roundEvent);

        eventManager.registerRoundStateChangeHandler(roundStateChangeEventHandler2);
        eventManager.dispatchRoundChangeEvent(roundEvent);

        verify(roundStateChangeEventHandler1, times(2)).onRoundChangeEvent(roundEvent);
        verify(roundStateChangeEventHandler2, times(1)).onRoundChangeEvent(roundEvent);
    }

    @Test
    public void dispatchPlayerChangeEvent() {
        eventManager.registerPlayerStateChangeEvent(playerStateChangeHandler1);
        eventManager.dispatchPlayerChangeEvent(playerEvent);

        eventManager.registerPlayerStateChangeEvent(playerStateChangeHandler2);
        eventManager.dispatchPlayerChangeEvent(playerEvent);

        verify(playerStateChangeHandler1, times(2)).onPlayerStateChange(playerEvent);
        verify(playerStateChangeHandler2, times(1)).onPlayerStateChange(playerEvent);
    }
}