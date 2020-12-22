package com.tj.cardsagainsthumanity.server.protocol.impl.processor;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.models.gameStatus.GameStatus;
import com.tj.cardsagainsthumanity.models.gameStatus.GameStatusFactory;
import com.tj.cardsagainsthumanity.models.gameplay.Game;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseResponse;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.CreateGameCommand;
import com.tj.cardsagainsthumanity.server.protocol.message.CommandContext;
import com.tj.cardsagainsthumanity.services.gameplay.GameService;
import com.tj.cardsagainsthumanity.services.gameplay.PlayerService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class CreateGameCommandProcessorTest {
    CreateGameCommandProcessor processor;
    BaseResponse<GameStatus> result;
    @Mock
    PlayerService playerService;
    @Mock
    GameService gameService;
    @Mock
    GameDriver mockCreatedDriver;
    @Mock
    CommandContext context;
    @Mock
    CreateGameCommand message;
    @Mock
    Player currentPlayer;
    @Mock
    GameStatusFactory gameStatusFactory;

    private Integer gameId = 5;
    private Game.GameState gameState = Game.GameState.INITIALIZING;
    private String gameCode = "someCode";

    @Before
    public void beforeEach() {
        processor = new CreateGameCommandProcessor(gameService, playerService, gameStatusFactory);
        when(gameService.newGame()).thenReturn(mockCreatedDriver);
        when(mockCreatedDriver.getGameId()).thenReturn(gameId);
        when(mockCreatedDriver.getState()).thenReturn(gameState);
        when(mockCreatedDriver.getCode()).thenReturn(gameCode);
        when(context.getPlayer()).thenReturn(Optional.of(currentPlayer));

        result = processor.processMessage(message, context);
    }


    @Test
    public void itCreatesNewGame() {
        verify(mockCreatedDriver, times(1)).save();
    }


    @Test
    public void itSavesGame() {
        verify(mockCreatedDriver, times(1)).save();
    }

    @Test
    public void itJoinsGame() {
        verify(gameService, times(1)).joinGame(currentPlayer, gameCode);
    }

    @Test
    public void itReturnsExpectedResponse() {
        GameStatus body = result.getBody();
        assertEquals(result.getStatus(), 201);
        assertEquals(result.getStatusMessage(), "Created");
        assertEquals(body.getState(), gameState);
        assertEquals(body.getGameId(), gameId);
        assertEquals(body.getCode(), gameCode);
    }
}