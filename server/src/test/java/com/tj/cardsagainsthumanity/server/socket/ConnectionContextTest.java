package com.tj.cardsagainsthumanity.server.socket;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.core.game.events.handler.GameJoinedHandler;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionContextTest {

    @Mock
    Player mockPlayer;
    @Mock
    GameDriver driver;
    @Mock
    GameJoinedHandler handler;

    private ConnectionContext context;

    @Before
    public void setUp() throws Exception {
        context = new ConnectionContext();
    }

    @Test
    public void itInitiallyHasNoLoggedInPlayer() {
        assertEquals(context.getPlayer(), Optional.empty());
    }

    @Test
    public void itInitiallyHasNoJoinedGame() {
        assertEquals(context.getCurrentGame(), Optional.empty());
    }

    @Test
    public void itInitiallyHasNoJoinedGameHandler() {
        assertEquals(context.getCurrentJoinHandler(), Optional.empty());
    }

    @Test
    public void itAddsPlayerToContextWhenLoggedIn() {
        context.login(mockPlayer);
        assertEquals(context.getPlayer(), Optional.of(mockPlayer));
    }

    @Test
    public void itResetsPlayerOnLogout() {
        context.login(mockPlayer);
        assertEquals(context.getPlayer(), Optional.of(mockPlayer));
        context.logout();
        assertEquals(context.getPlayer(), Optional.empty());
    }

    @Test
    public void itAddsDriverToContextWhenGameIsJoined() {
        context.joinGame(driver);
        assertEquals(context.getCurrentGame(), Optional.of(driver));
    }

    @Test
    public void itRegistersGameJoinHandler() {
        context.setGameHandler(handler);
        assertEquals(context.getCurrentJoinHandler(), Optional.of(handler));
    }

}