package com.tj.cardsagainsthumanity.server;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.core.game.events.handler.*;
import com.tj.cardsagainsthumanity.core.game.events.types.GameEvent;
import com.tj.cardsagainsthumanity.core.game.events.types.PlayerEvent;
import com.tj.cardsagainsthumanity.core.game.events.types.RoundEvent;
import com.tj.cardsagainsthumanity.models.gameStatus.GameStatus;
import com.tj.cardsagainsthumanity.models.gameStatus.GameStatusFactory;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.server.protocol.CommandProcessor;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.GameStatusCommand;
import com.tj.cardsagainsthumanity.server.protocol.message.Command;
import com.tj.cardsagainsthumanity.server.protocol.message.Response;
import com.tj.cardsagainsthumanity.server.socket.ConnectionContext;

import java.io.IOException;

public abstract class AbstractConnection implements RoundStateChangeEventHandler, PlayerStateChangeHandler, RoundStartedEventHandler, GameJoinedHandler, GameStartedEventHandler, GameStateChangeEventHandler {
    private final CommandProcessor<Command, Response> commandProcessor;
    private final ConnectionContext connectionContext;
    private final GameStatusFactory statusFactory;

    public AbstractConnection(CommandProcessor<Command, Response> commandProcessor, GameStatusFactory gameStatusFactory) {
        this.commandProcessor = commandProcessor;
        this.connectionContext = new ConnectionContext(this);
        this.statusFactory = gameStatusFactory;
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
    public void onGameLeft(GameDriver game) {
        game.unregisterGameStartedHandler(this);
        game.unregisterRoundStateChangeHandler(this);
        game.unregisterRoundStartedHandler(this);
        game.unregisterPlayerStateChangeEvent(this);
        game.unregisterGameStateChangeHandler(this);
    }

    @Override
    public void onGameStateChange(GameEvent game) {
        sendGameStatus(game.getGame());
    }

    @Override
    public void onPlayerStateChange(PlayerEvent event) {
        sendGameStatus(event.getGame());
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


    protected GameStatusCommand createGameStatusCommand(GameDriver game) {
        Player currentPlayer = getCurrentPlayer();
        GameStatus request = statusFactory.buildGameStatus(currentPlayer, game);
        return new GameStatusCommand(request);
    }

    protected Player getCurrentPlayer() {
        return getConnectionContext().getPlayer().orElseThrow(() -> new IllegalStateException("Game status cannot be retrieved without current player"));
    }

    protected void sendGameStatus(GameDriver game) {
        this.sendCommand(createGameStatusCommand(game));
    }

    public abstract boolean isConnectionAlive();


    public abstract void closeConnection() throws IOException;

    public abstract void sendCommand(Command command);

    public abstract void sendResponse(Response command);
}
