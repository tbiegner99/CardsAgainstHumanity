package com.tj.cardsagainsthumanity.server.socket;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.core.game.RoundDriver;
import com.tj.cardsagainsthumanity.core.game.events.handler.GameJoinedHandler;
import com.tj.cardsagainsthumanity.core.game.events.handler.GameStartedEventHandler;
import com.tj.cardsagainsthumanity.core.game.events.handler.RoundStartedEventHandler;
import com.tj.cardsagainsthumanity.core.game.events.handler.RoundStateChangeEventHandler;
import com.tj.cardsagainsthumanity.core.game.events.types.GameEvent;
import com.tj.cardsagainsthumanity.core.game.events.types.RoundEvent;
import com.tj.cardsagainsthumanity.models.gameplay.GameRound;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.server.protocol.CommandProcessor;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.GameStatusCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.GameStatus;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.EmptyResponse;
import com.tj.cardsagainsthumanity.server.protocol.io.ProtocolReader;
import com.tj.cardsagainsthumanity.server.protocol.io.ProtocolWriter;
import com.tj.cardsagainsthumanity.server.protocol.message.Command;
import com.tj.cardsagainsthumanity.server.protocol.message.Message;
import com.tj.cardsagainsthumanity.server.protocol.message.Response;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class PlayerConnection implements Runnable, RoundStateChangeEventHandler, RoundStartedEventHandler, GameJoinedHandler, GameStartedEventHandler {
    private final ProtocolReader protocolReader;
    private final CommandProcessor<Command, Response> commandProcessor;
    private final ProtocolWriter protocolWriter;
    private final ConnectionCloseHandler closeHandler;
    private final String connectionName;
    private Thread thread;
    private Queue<Command> waitingNotifications;
    private boolean connectionAlive = true;
    private ConnectionContext context;

    public PlayerConnection(String connectionName, ConnectionCloseHandler closeHandler, ProtocolReader reader, ProtocolWriter writer, CommandProcessor<Command, Response> commandProcessor) {
        this.protocolReader = reader;
        this.protocolWriter = writer;
        this.commandProcessor = commandProcessor;
        this.closeHandler = closeHandler;
        this.waitingNotifications = new LinkedList<>();
        this.connectionName = connectionName;

    }

    public Thread getConnectionThread() {
        if (thread == null) {
            thread = new Thread(this, "Connection:" + connectionName);
        }
        return thread;
    }

    public void startConnection() throws IllegalThreadStateException {
        getConnectionThread().start();
    }

    public Queue<Command> getWaitingNotifications() {
        return new LinkedList<>(waitingNotifications);
    }

    public synchronized void queueCommand(Command notification) {
        waitingNotifications.add(notification);
    }

    private synchronized void sendWaitingCommands() {
        while (!waitingNotifications.isEmpty()) {
            Command nextCommand = waitingNotifications.poll();
            try {
                protocolWriter.sendCommand(nextCommand);
            } catch (IOException e){}
        }
    }

    public boolean isConnectionAlive() {
        return connectionAlive;
    }

    public ConnectionContext getCurrentContext() {
        return context;
    }

    public void closeConnection() {
        connectionAlive = false;
        closeHandler.onConnectionClosed(this);
    }

    @Override
    public void run() {
        System.out.println("Connection acquired...");
        context = new ConnectionContext(this);
        while (isConnectionAlive()) {
            readAndProcessMessage(context);
        }
    }


    void assertThreadNotInterrupted() throws InterruptedException {
        if (getConnectionThread().isInterrupted()) {
            throw new InterruptedException();
        }
    }

    Message readMessage() throws InterruptedException {
        while (true) {
            try {
                return protocolReader.readMessage();
            } catch (InterruptedIOException ex) {
                throw new InterruptedException();
            } catch (IOException e) {
                assertThreadNotInterrupted();
            }
        }
    }

    void readAndProcessMessage(ConnectionContext context) {
        try {
            Message message = readMessage();
            if (message.getMessageType() == Message.Type.RESPONSE) {
                return;
            }
            processMessage(context, (Command) message);
        } catch (InterruptedException ex) {
            sendWaitingCommands();
        }
    }

    private void processMessage(ConnectionContext context, Command message) {
        try {
            Response response = commandProcessor.processMessage(message, context);
            protocolWriter.sendResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                protocolWriter.sendResponse(generateErrorResponse(e, message));
            }catch(IOException ex){}
        }
    }

    private Response generateErrorResponse(Exception e, Command message) {
        return EmptyResponse.INTERNAL_SERVER_ERROR.forMessage(message.getMessageId());
    }


    @Override
    public void onGameStarted(GameEvent game) {
        sendGameStatus(game.getGame());
    }

    private void sendGameStatus(GameDriver game) {
        GameStatusCommand cmd = createGameStatusCommand(game);
        queueCommand(cmd);
        getConnectionThread().interrupt();
    }

    @Override
    public void onRoundChangeEvent(RoundEvent evt) {
        RoundDriver driver = evt.getRound();
        GameRound round = driver.getRound();
        boolean isCzar = getCurrentContext().getPlayer().get().isCzarFor(round);
        if (isCzar) {
            sendGameStatus(evt.getGame());
        }
    }

    private GameStatusCommand createGameStatusCommand(GameDriver game) {
        Player currentPlayer = getCurrentContext().getPlayer().orElseThrow(() -> new IllegalStateException("Game status cannot be retrieved without current player"));
        GameStatus request = GameStatus.fromGame(game, currentPlayer);
        return new GameStatusCommand(request);
    }


    @Override
    public void onGameJoined(GameDriver game) {
        game.registerGameStartedHandler(this);
        game.registerRoundStateChangeHandler(this);
        game.registerRoundStartedHandler(this);
    }

    @Override
    public boolean equals(Object o) {
        PlayerConnection that = (PlayerConnection) o;
        return Objects.equals(protocolReader, that.protocolReader) &&
                Objects.equals(commandProcessor, that.commandProcessor) &&
                Objects.equals(protocolWriter, that.protocolWriter) &&
                Objects.equals(connectionName, that.connectionName) &&
                Objects.equals(closeHandler, that.closeHandler);
    }


    @Override
    public void onRoundStart(RoundEvent evt) {
        GameStatusCommand cmd = createGameStatusCommand(evt.getGame());
        queueCommand(cmd);
        getConnectionThread().interrupt();
    }
}
