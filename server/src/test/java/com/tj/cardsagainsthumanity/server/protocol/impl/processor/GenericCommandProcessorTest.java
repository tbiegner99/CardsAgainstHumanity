package com.tj.cardsagainsthumanity.server.protocol.impl.processor;

import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseResponse;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.CreateGameCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.JoinGameCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.LoginCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.StartGameCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.gameplay.ChooseWinnerCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.gameplay.PlayCardCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.EmptyResponse;
import com.tj.cardsagainsthumanity.server.protocol.impl.processor.gameplay.ChooseWinnerProcessor;
import com.tj.cardsagainsthumanity.server.protocol.impl.processor.gameplay.PlayCardCommandProcessor;
import com.tj.cardsagainsthumanity.server.protocol.message.Command;
import com.tj.cardsagainsthumanity.server.protocol.message.CommandContext;
import com.tj.cardsagainsthumanity.server.protocol.message.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GenericCommandProcessorTest {
    GenericCommandProcessor processor;
    @Mock
    CreateGameCommandProcessor createGameCommandProcessor;
    CreateGameCommand createGameCommand = new CreateGameCommand();
    @Mock
    JoinGameCommandProcessor joinGameCommandProcessor;
    JoinGameCommand joinGameCommand = new JoinGameCommand();
    @Mock
    LoginCommandProcessor loginCommandProcessor;
    LoginCommand loginCommand = new LoginCommand();
    @Mock
    StartGameCommandProcessor startGameCommandProcessor;
    StartGameCommand startGameCommand = new StartGameCommand();
    @Mock
    PlayCardCommandProcessor playCardCommandProcessor;
    PlayCardCommand playCardCommand = new PlayCardCommand();

    @Mock
    ChooseWinnerProcessor chooseWinnerProcessor;
    ChooseWinnerCommand chooseWinnerCommand = new ChooseWinnerCommand();
    @Mock
    CommandContext context;
    @Mock
    BaseResponse mockResponse;
    @Mock
    Player loggedInPlayer;
    @Mock
    Command genericCommand;


    @Before
    public void setUp() throws Exception {
        processor = new GenericCommandProcessor(startGameCommandProcessor, loginCommandProcessor, createGameCommandProcessor, joinGameCommandProcessor, playCardCommandProcessor, chooseWinnerProcessor);
        when(context.getPlayer()).thenReturn(Optional.of(loggedInPlayer));
        when(loginCommandProcessor.processMessage(loginCommand, context)).thenReturn(mockResponse);
        when(createGameCommandProcessor.processMessage(createGameCommand, context)).thenReturn(mockResponse);
        when(startGameCommandProcessor.processMessage(startGameCommand, context)).thenReturn(EmptyResponse.NO_CONTENT);
        when(joinGameCommandProcessor.processMessage(joinGameCommand, context)).thenReturn(mockResponse);
    }

    @Test
    public void processMessageForLoginCommand() {
        Response response = processor.processMessage(loginCommand, context);
        verify(loginCommandProcessor, times(1)).processMessage(loginCommand, context);
        assertSame(response, mockResponse);
    }

    @Test
    public void processMessageForLoginCommand_whenNoCurrentPlayer() {
        when(context.getPlayer()).thenReturn(Optional.empty());
        Response response = processor.processMessage(loginCommand, context);
        verify(loginCommandProcessor, times(1)).processMessage(loginCommand, context);
        assertSame(response, mockResponse);
    }

    @Test
    public void processMessageForCommand_whenLoginIsRequiredAndNoLoggedInPlayer() {
        when(context.getPlayer()).thenReturn(Optional.empty());
        Response response = processor.processMessage(createGameCommand, context);
        verify(createGameCommandProcessor, times(0)).processMessage(createGameCommand, context);
        assertSame(response, EmptyResponse.FORBIDDEN);
    }

    @Test
    public void processMessageForCreateGameCommand() {
        Response response = processor.processMessage(createGameCommand, context);
        verify(createGameCommandProcessor, times(1)).processMessage(createGameCommand, context);
        assertSame(response, mockResponse);
    }

    @Test
    public void processMessageForJoinGameCommand() {
        Response response = processor.processMessage(joinGameCommand, context);
        verify(joinGameCommandProcessor, times(1)).processMessage(joinGameCommand, context);
        assertSame(response, mockResponse);
    }

    @Test
    public void processMessageForStartGameCommand() {
        Response response = processor.processMessage(startGameCommand, context);
        verify(startGameCommandProcessor, times(1)).processMessage(startGameCommand, context);
        assertSame(response, EmptyResponse.NO_CONTENT);
    }

    @Test
    public void processUnknownCommand() {
        when(genericCommand.getCommandName()).thenReturn("Unknown Command");
        Response response = processor.processMessage(genericCommand, context);
        assertSame(response, EmptyResponse.METHOD_NOT_FOUND);
    }

    @Test
    public void processMessageForCommand_whenExceptionThrown() {
        when(loginCommandProcessor.processMessage(loginCommand, context)).thenThrow(new IllegalArgumentException());
        Response response = processor.processMessage(loginCommand, context);
        verify(loginCommandProcessor, times(1)).processMessage(loginCommand, context);
        assertSame(response, EmptyResponse.INTERNAL_SERVER_ERROR);
    }

}