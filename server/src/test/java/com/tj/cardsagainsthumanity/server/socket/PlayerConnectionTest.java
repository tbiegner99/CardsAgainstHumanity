package com.tj.cardsagainsthumanity.server.socket;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.core.game.RoundDriver;
import com.tj.cardsagainsthumanity.core.game.events.types.GameEvent;
import com.tj.cardsagainsthumanity.core.game.events.types.RoundEvent;
import com.tj.cardsagainsthumanity.models.cards.WhiteCard;
import com.tj.cardsagainsthumanity.models.gameStatus.GameStatus;
import com.tj.cardsagainsthumanity.models.gameStatus.GameStatusFactory;
import com.tj.cardsagainsthumanity.models.gameplay.Game;
import com.tj.cardsagainsthumanity.models.gameplay.GameRound;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.server.protocol.CommandProcessor;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseResponse;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.GameStatusCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.EmptyResponse;
import com.tj.cardsagainsthumanity.server.protocol.io.ProtocolReader;
import com.tj.cardsagainsthumanity.server.protocol.io.ProtocolWriter;
import com.tj.cardsagainsthumanity.server.protocol.message.Command;
import com.tj.cardsagainsthumanity.server.protocol.message.Message;
import com.tj.cardsagainsthumanity.server.protocol.message.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlayerConnectionTest {
    PlayerConnection connection;
    @Mock
    ProtocolReader reader;
    @Mock
    ProtocolWriter writer;
    @Mock
    ConnectionCloseHandler closeHandler;
    @Mock
    CommandProcessor<Command, Response> commandProcessor;
    @Mock
    BaseCommand command1;
    @Mock
    Command command2;
    @Mock
    BaseResponse baseResponse;
    @Mock
    GameDriver driver;
    @Mock
    RoundDriver currentRoundDriver;
    @Mock
    Game mockGame;
    @Mock
    GameEvent event;
    @Mock
    RoundEvent roundEvent;
    @Mock
    GameRound round;
    @Mock
    Player currentPlayer;
    @Mock
    WhiteCard playerHandCard;
    @Mock
    ConnectionContext context;

    @Mock
    GameStatusFactory gameStatusFactory;

    @Mock
    Thread thread;

    @Before
    public void setUp() throws Exception {
        PlayerConnection conn = new PlayerConnection("someName", closeHandler, reader, writer, commandProcessor, gameStatusFactory);
        connection = spy(conn);
        when(event.getGame()).thenReturn(driver);
        when(command1.getMessageId()).thenReturn("someId");
        when(baseResponse.getMessageType()).thenReturn(Message.Type.RESPONSE);
        when(driver.getGameId()).thenReturn(8);
        when(driver.getGame()).thenReturn(mockGame);
        when(currentRoundDriver.getRound()).thenReturn(round);
        when(roundEvent.getRound()).thenReturn(currentRoundDriver);
        Map<Player, Set<WhiteCard>> mockHands = new HashMap<>();
        Set<WhiteCard> currentPlayerHand = new HashSet<>();
        mockHands.put(currentPlayer, currentPlayerHand);
        when(mockGame.getPlayerHands()).thenReturn(mockHands);
    }

    @Test
    public void startConnection() throws Exception {
        doAnswer(invocation -> {
            connection.closeConnection();
            return null;
        }).when(connection).readAndProcessMessage(any());
        connection.startConnection();
        connection.getConnectionThread().join();
        verify(connection, times(1)).readAndProcessMessage(any());
    }


    @Test
    public void startConnection_mayOnlyBeCalledOnce() throws Exception {
        doAnswer(invocation -> {
            connection.closeConnection();
            return null;
        }).when(connection).readAndProcessMessage(any());
        connection.startConnection();
        connection.getConnectionThread().join();
        verify(connection, times(1)).readAndProcessMessage(any());
        try {
            connection.startConnection();
            fail();
        } catch (IllegalThreadStateException e) {
            verify(connection, times(1)).readAndProcessMessage(any());
            return;
        }

    }

    @Test
    public void queueCommand() {
        assertEquals(connection.getWaitingNotifications().size(), 0);
        connection.queueCommand(command1);
        assertEquals(connection.getWaitingNotifications().size(), 1);
        connection.queueCommand(command2);
        assertEquals(connection.getWaitingNotifications().size(), 2);
        assertSame(connection.getWaitingNotifications().peek(), command1);
    }

    @Test
    public void closeConnection() {
        assertTrue(connection.isConnectionAlive());
        connection.closeConnection();
        assertFalse(connection.isConnectionAlive());
        verify(closeHandler, times(1)).onConnectionClosed(connection);
    }

    @Test
    public void run() {
        doAnswer(invocation -> {
            connection.closeConnection();
            return null;
        }).when(connection).readAndProcessMessage(any());
        connection.run();
        ConnectionContext context = connection.getCurrentContext();
        assertEquals(context.getCurrentJoinHandler(), Optional.of(connection));
        verify(connection, times(1)).readAndProcessMessage(context);
    }

    @Test
    public void readAndProcessMessage_whenThreadIsInterruptedAndIoException_throwsInterruptedException() throws Exception {
        when(reader.readMessage()).thenThrow(new IOException());
        doReturn(thread).when(connection).getConnectionThread();
        when(thread.isInterrupted()).thenReturn(true);
        when(commandProcessor.processMessage(command1, context)).thenThrow(new IllegalArgumentException());
        connection.queueCommand(command1);
        connection.queueCommand(command2);
        connection.readAndProcessMessage(context);
        verify(writer, times(1)).sendCommand(command1);
        verify(writer, times(1)).sendCommand(command2);
        assertEquals(connection.getWaitingNotifications().size(), 0);
    }

    @Test
    public void readMessage_whenThreadIsNotInterruptedAndIoException_triesReadAgain() throws Exception {
        when(reader.readMessage()).thenThrow(new IOException()).thenReturn(command1);
        doReturn(thread).when(connection).getConnectionThread();
        when(thread.isInterrupted()).thenReturn(false);
        Message ret = connection.readMessage();
        verify(reader, times(2)).readMessage();
    }

    @Test
    public void readAndProcessMessage_whenCommandIsRead() throws Exception {
        when(reader.readMessage()).thenReturn(command1);
        when(commandProcessor.processMessage(command1, context)).thenReturn(baseResponse);
        connection.readAndProcessMessage(context);
        verify(commandProcessor, times(1)).processMessage(command1, context);
        verify(writer, times(1)).sendResponse(baseResponse);
    }

    @Test
    public void readAndProcessMessage_whenResponseIsRead_noProcessingOccurs() throws Exception {
        when(reader.readMessage()).thenReturn(baseResponse);
        when(commandProcessor.processMessage(any(), eq(context))).thenReturn(baseResponse);
        connection.readAndProcessMessage(context);
        verify(commandProcessor, times(0)).processMessage(command1, context);
        verify(writer, times(0)).sendResponse(baseResponse);
    }


    @Test
    public void readAndProcessMessage_whenCommandIsRead_andProcessingErrorOccurs() throws Exception {
        when(reader.readMessage()).thenReturn(command1);
        when(commandProcessor.processMessage(command1, context)).thenThrow(new IllegalArgumentException());
        connection.readAndProcessMessage(context);
        verify(commandProcessor, times(1)).processMessage(command1, context);
        verify(writer, times(1)).sendResponse(EmptyResponse.INTERNAL_SERVER_ERROR.forMessage("someId"));
    }


    @Test
    public void readAndProcessMessage_whenThreadIsInterrupted_sendsWaitingCommands() throws Exception {
        when(reader.readMessage()).thenThrow(new InterruptedIOException());
        when(commandProcessor.processMessage(command1, context)).thenThrow(new IllegalArgumentException());
        connection.queueCommand(command1);
        connection.queueCommand(command2);
        connection.readAndProcessMessage(context);
        verify(writer, times(1)).sendCommand(command1);
        verify(writer, times(1)).sendCommand(command2);
        assertEquals(connection.getWaitingNotifications().size(), 0);
    }

    @Test
    public void onGameStarted() {
        doReturn(Optional.of(currentPlayer)).when(context).getPlayer();
        doReturn(context).when(connection).getCurrentContext();
        doReturn(thread).when(connection).getConnectionThread();
        GameStatus expectedStatus = gameStatusFactory.buildGameStatus(currentPlayer, event.getGame());
        GameStatusCommand expected = new GameStatusCommand(expectedStatus); // GameCommand(new GameRequest(8));
        connection.onGameStarted(event);
        assertEquals(expected, connection.getWaitingNotifications().peek());
        verify(thread, times(1)).interrupt();
    }

    @Test
    public void onRoundStart_sendsUpdatedGameStatus() {
        doReturn(driver).when(roundEvent).getGame();
        doReturn(Optional.of(currentPlayer)).when(context).getPlayer();
        doReturn(context).when(connection).getCurrentContext();
        doReturn(thread).when(connection).getConnectionThread();
        doReturn(true).when(currentPlayer).isCzarFor(round);
        GameStatus expectedStatus = gameStatusFactory.buildGameStatus(currentPlayer, event.getGame());
        GameStatusCommand expected = new GameStatusCommand(expectedStatus); // GameCommand(new GameRequest(8));
        connection.onRoundStart(roundEvent);
        assertEquals(expected, connection.getWaitingNotifications().peek());
        verify(thread, times(1)).interrupt();
    }

    @Test
    public void onRoundChangeEvent_whenPlayerIsCzarSendsGameStatus() {
        doReturn(driver).when(roundEvent).getGame();
        doReturn(Optional.of(currentPlayer)).when(context).getPlayer();
        doReturn(context).when(connection).getCurrentContext();
        doReturn(thread).when(connection).getConnectionThread();
        doReturn(true).when(currentPlayer).isCzarFor(round);
        GameStatus expectedStatus = gameStatusFactory.buildGameStatus(currentPlayer, event.getGame());
        GameStatusCommand expected = new GameStatusCommand(expectedStatus); // GameCommand(new GameRequest(8));
        connection.onRoundChangeEvent(roundEvent);
        assertEquals(expected, connection.getWaitingNotifications().peek());
        verify(thread, times(1)).interrupt();
    }

    @Test
    public void onRoundChangeEvent_whenPlayerIsNotCzar_doesNotSendGameStatus() {
        doReturn(Optional.of(currentPlayer)).when(context).getPlayer();
        doReturn(context).when(connection).getCurrentContext();
        doReturn(thread).when(connection).getConnectionThread();
        doReturn(false).when(currentPlayer).isCzarFor(round);
        connection.onRoundChangeEvent(roundEvent);
        assertTrue(connection.getWaitingNotifications().isEmpty());
        verify(thread, times(0)).interrupt();
    }

    @Test
    public void onGameJoined() {
        connection.onGameJoined(driver);
        verify(driver, times(1)).registerGameStartedHandler(connection);
    }


}