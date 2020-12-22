package com.tj.cardsagainsthumanity.server.protocol.impl.processor;

import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.StartGameCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.GameRequest;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.EmptyResponse;
import com.tj.cardsagainsthumanity.server.protocol.message.CommandContext;
import com.tj.cardsagainsthumanity.server.protocol.message.Response;
import com.tj.cardsagainsthumanity.services.gameplay.GameService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StartGameCommandProcessorTest {
    StartGameCommandProcessor processor;
    Response result;
    @Mock
    GameService gameService;
    @Mock
    CommandContext context;
    @Mock
    StartGameCommand message;
    @Mock
    GameRequest gameRequest;

    private Integer gameId = 7;

    @Before
    public void beforeEach() {
        processor = new StartGameCommandProcessor(gameService);
        when(gameRequest.getGameId()).thenReturn(gameId);
        when(message.getMessageId()).thenReturn("someId");
        when(message.getArguments()).thenReturn(gameRequest);

        result = processor.processMessage(message, context);
    }

    @Test
    public void itStartsTheRequestedGame() {
        verify(gameService, times(1)).startGame(gameId);
    }

    @Test
    public void itReturnsExpectedResponse() {
        assertEquals(result, EmptyResponse.NO_CONTENT.forMessage("someId"));
    }
}