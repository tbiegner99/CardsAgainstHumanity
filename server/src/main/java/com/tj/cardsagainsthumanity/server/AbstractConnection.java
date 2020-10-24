package com.tj.cardsagainsthumanity.server;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.core.game.events.handler.*;
import com.tj.cardsagainsthumanity.core.game.events.types.GameEvent;
import com.tj.cardsagainsthumanity.core.game.events.types.PlayerEvent;
import com.tj.cardsagainsthumanity.core.game.events.types.RoundEvent;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.server.protocol.CommandProcessor;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.GameStatusCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.ExtendedGameStatus;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.GameStatus;
import com.tj.cardsagainsthumanity.server.protocol.message.Command;
import com.tj.cardsagainsthumanity.server.protocol.message.Response;
import com.tj.cardsagainsthumanity.server.socket.ConnectionContext;

import java.io.IOException;

public abstract class AbstractConnection implements RoundStateChangeEventHandler, PlayerStateChangeHandler, RoundStartedEventHandler, GameJoinedHandler, GameStartedEventHandler, GameStateChangeEventHandler {
    private final CommandProcessor<Command, Response> commandProcessor;
    private final ConnectionContext connectionContext;

    public AbstractConnection(CommandProcessor<Command, Response> commandProcessor) {
        this.commandProcessor = commandProcessor;
        this.connectionContext = new ConnectionContext(this);
    }

    public CommandProcessor<Command, Response> getCommandProcessor() {
        return commandProcessor;
    }

    public ConnectionContext getConnectionContext() {
        return connectionContext;
    }

    @Override
    public void onGameJoined(GameDriver game) {
        game.registerGameStartedHandler(this);
        game.registerRoundStateChangeHandler(this);
        game.registerRoundStartedHandler(this);
        game.registerPlayerStateChangeEvent(this);
        game.registerGameStateChangeHandler(this);
    }

    @Override
    public void onGameStateChange(GameEvent game) {
        sendGameStatus(game.getGame());
    }

    @Override
    public void onPlayerStateChange(PlayerEvent event) {
        sendExtendedGameStatus(event.getGame());
    }

    @Override
    public void onGameStarted(GameEvent game) {
        sendGameStatus(game.getGame());
    }

    @Override
    public void onRoundChangeEvent(RoundEvent evt) {
        sendGameStatus(evt.getGame());
    }

    @Override
    public void onRoundStart(RoundEvent evt) {
        sendGameStatus(evt.getGame());
    }

    protected Player getCurrentPlayer(GameDriver driver) {
        return getConnectionContext().getPlayer().orElseThrow(() -> new IllegalStateException("Game status cannot be retrieved without current player"));
    }

    protected GameStatusCommand createExtendedGameStatusCommand(GameDriver game) {
        Player currentPlayer = getCurrentPlayer(game);
        GameStatus request = ExtendedGameStatus.fromGame(game, currentPlayer);
        return new GameStatusCommand(request);
    }

    protected GameStatusCommand createGameStatusCommand(GameDriver game) {
        Player currentPlayer = getCurrentPlayer(game);
        GameStatus request = GameStatus.fromGame(game, currentPlayer);
        return new GameStatusCommand(request);
    }

    protected void sendGameStatus(GameDriver game) {
        this.sendCommand(createGameStatusCommand(game));
    }

    protected void sendExtendedGameStatus(GameDriver game) {
        this.sendCommand(createExtendedGameStatusCommand(game));
    }


    public abstract boolean isConnectionAlive();


    public abstract void closeConnection() throws IOException;

    public abstract void sendCommand(Command command);

    public abstract void sendResponse(Response command);
}
