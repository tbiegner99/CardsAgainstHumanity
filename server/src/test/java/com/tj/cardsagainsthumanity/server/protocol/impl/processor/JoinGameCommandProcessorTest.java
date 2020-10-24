package com.tj.cardsagainsthumanity.server.protocol.impl.processor;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.models.gameplay.Game;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseResponse;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.JoinGameCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.JoinGameRequest;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.body.GameResponseBody;
import com.tj.cardsagainsthumanity.server.protocol.message.CommandContext;
import com.tj.cardsagainsthumanity.services.gameplay.GameService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class JoinGameCommandProcessorTest {
    JoinGameCommandProcessor processor;
    BaseResponse<GameResponseBody> result;
    @Mock
    GameService gameService;
    @Mock
    GameDriver mockCreatedDriver;
    @Mock
    CommandContext context;
    @Mock
    JoinGameCommand message;
    @Mock
    JoinGameRequest mockRequest;
    @Mock
    Player currentPlayer;

    private Integer gameId = 5;
    private Game.GameState gameState = Game.GameState.INITIALIZING;
    private String gameCode = "someCode";

    @Before
    public void beforeEach() {
        processor = new JoinGameCommandProcessor(gameService);
        when(gameService.joinGame(currentPlayer, gameCode)).thenReturn(mockCreatedDriver);
        when(message.getArguments()).thenReturn(mockRequest);
        when(mockRequest.getCode()).thenReturn(gameCode);
        when(mockCreatedDriver.getGameId()).thenReturn(gameId);
        when(mockCreatedDriver.getState()).thenReturn(gameState);
        when(mockCreatedDriver.getCode()).thenReturn(gameCode);
        when(context.getPlayer()).thenReturn(Optional.of(currentPlayer));

        result = processor.processMessage(message, context);
    }


    @Test
    public void itSetsGameInConnectionContext() {
        verify(gameService, times(1)).joinGame(currentPlayer, gameCode);
    }

    @Test
    public void itAddsCurrentPlayerToGame() {
        verify(context, times(1)).joinGame(mockCreatedDriver);
    }

    @Test
    public void itReturnsExpectedResponse() {
        GameResponseBody body = result.getBody();
        assertEquals(result.getStatus(), 200);
        assertEquals(result.getStatusMessage(), "OK");
        assertEquals(body.getState(), gameState);
        assertEquals(body.getGameId(), gameId);
        assertEquals(body.getCode(), gameCode);
    }
}