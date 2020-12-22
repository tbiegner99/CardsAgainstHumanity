package com.tj.cardsagainsthumanity.core.game.events;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.core.game.RoundDriver;
import com.tj.cardsagainsthumanity.core.game.events.types.GameEvent;
import com.tj.cardsagainsthumanity.core.game.events.types.PlayerEvent;
import com.tj.cardsagainsthumanity.core.game.events.types.RoundEvent;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.TestCase.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class EventFactoryTest {
    @Mock
    private GameDriver driver;
    @Mock
    private RoundDriver round;
    @Mock
    private Player player;

    private EventFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new EventFactory();
    }

    @Test
    public void createGameStartedEvent() {
        GameEvent result = factory.createGameStartedEvent(driver);
        GameEvent expectedResult = new GameEvent(driver, EventName.GameEvents.GAME_STARTED);
        assertEquals(expectedResult, result);
    }

    @Test
    public void createGameOverEvent() {
        GameEvent result = factory.createGameOverEvent(driver);
        GameEvent expectedResult = new GameEvent(driver, EventName.GameEvents.GAME_OVER);
        assertEquals(expectedResult, result);
    }

    @Test
    public void createGameChangeEvent() {
        GameEvent result = factory.createGameChangeEvent(driver);
        GameEvent expectedResult = new GameEvent(driver, EventName.GameEvents.GAME_STATE_CHANGE);
        assertEquals(expectedResult, result);
    }

    @Test
    public void createRoundChangeEvent() {
        RoundEvent result = factory.createRoundChangeEvent(driver, round);
        RoundEvent expectedResult = new RoundEvent(driver, round, EventName.RoundEvents.ROUND_STATE_CHANGE);
        assertEquals(expectedResult, result);
    }

    @Test
    public void createRoundStartedEvent() {
        RoundEvent result = factory.createRoundStartedEvent(driver, round);
        RoundEvent expectedResult = new RoundEvent(driver, round, EventName.RoundEvents.ROUND_STARTED);
        assertEquals(expectedResult, result);
    }

    @Test
    public void createRoundOverEvent() {
        RoundEvent result = factory.createRoundOverEvent(driver, round);
        RoundEvent expectedResult = new RoundEvent(driver, round, EventName.RoundEvents.ROUND_OVER);
        assertEquals(expectedResult, result);
    }

    @Test
    public void createPlayerHandChangeEvent() {
        PlayerEvent result = factory.createPlayerHandChangeEvent(driver, player);
        PlayerEvent expectedResult = new PlayerEvent(driver, player, EventName.PlayerEvents.PLAYER_HAND_CHANGED);
        assertEquals(expectedResult, result);
    }

    @Test
    public void createPlayerCreatedEvent() {
        PlayerEvent result = factory.createPlayerCreatedEvent(driver, player);
        PlayerEvent expectedResult = new PlayerEvent(driver, player, EventName.PlayerEvents.PLAYER_CREATED);
        assertEquals(expectedResult, result);
    }
}