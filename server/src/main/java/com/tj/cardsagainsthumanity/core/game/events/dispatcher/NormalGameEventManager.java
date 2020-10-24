package com.tj.cardsagainsthumanity.core.game.events.dispatcher;

import com.tj.cardsagainsthumanity.core.game.events.handler.*;
import com.tj.cardsagainsthumanity.core.game.events.types.GameEvent;
import com.tj.cardsagainsthumanity.core.game.events.types.PlayerEvent;
import com.tj.cardsagainsthumanity.core.game.events.types.RoundEvent;

import java.util.ArrayList;
import java.util.List;


public class NormalGameEventManager implements GameEventManager {
    private List<GameStartedEventHandler> gameStartedEventHandlerList;
    private List<GameOverEventHandler> gameOverEventHandlerList;
    private List<GameStateChangeEventHandler> gameStateChangeEventHandlerList;

    private List<RoundStartedEventHandler> roundStartedEventHandlerList;
    private List<RoundOverEventHandler> roundOverEventHandlerList;
    private List<RoundStateChangeEventHandler> roundStateChangeEventHandlerList;

    private List<PlayerStateChangeHandler> playerStateChangeHandlerList;


    public NormalGameEventManager() {
        gameStartedEventHandlerList = new ArrayList<>();
        gameOverEventHandlerList = new ArrayList<>();
        gameStateChangeEventHandlerList = new ArrayList<>();

        roundStartedEventHandlerList = new ArrayList<>();
        roundOverEventHandlerList = new ArrayList<>();
        roundStateChangeEventHandlerList = new ArrayList<>();

        playerStateChangeHandlerList = new ArrayList<>();

    }

    @Override
    public void registerGameStartedHandler(GameStartedEventHandler handler) {
        gameStartedEventHandlerList.add(handler);
    }

    @Override
    public void registerGameOverHandler(GameOverEventHandler handler) {
        gameOverEventHandlerList.add(handler);
    }

    @Override
    public void registerGameStateChangeHandler(GameStateChangeEventHandler handler) {
        gameStateChangeEventHandlerList.add(handler);
    }

    @Override
    public void registerRoundStartedHandler(RoundStartedEventHandler handler) {
        roundStartedEventHandlerList.add(handler);
    }

    @Override
    public void registerRoundOverHandler(RoundOverEventHandler handler) {
        roundOverEventHandlerList.add(handler);
    }

    @Override
    public void registerRoundStateChangeHandler(RoundStateChangeEventHandler handler) {
        roundStateChangeEventHandlerList.add(handler);
    }

    @Override
    public void registerPlayerStateChangeEvent(PlayerStateChangeHandler handler) {
        playerStateChangeHandlerList.add(handler);
    }

    @Override
    public void dispatchGameStartedEvent(GameEvent evt) {
        for (GameStartedEventHandler handler : gameStartedEventHandlerList) {
            handler.onGameStarted(evt);
        }
    }

    @Override
    public void dispatchGameOverEvent(GameEvent evt) {
        for (GameOverEventHandler handler : gameOverEventHandlerList) {
            handler.onGameOver(evt);
        }
    }

    @Override
    public void dispatchGameChangeEvent(GameEvent evt) {
        for (GameStateChangeEventHandler handler : gameStateChangeEventHandlerList) {
            handler.onGameStateChange(evt);
        }
    }

    @Override
    public void dispatchRoundStartedEvent(RoundEvent evt) {
        for (RoundStartedEventHandler handler : roundStartedEventHandlerList) {
            handler.onRoundStart(evt);
        }
    }

    @Override
    public void dispatchRoundOverEvent(RoundEvent evt) {
        for (RoundOverEventHandler handler : roundOverEventHandlerList) {
            handler.onRoundOver(evt);
        }
    }

    @Override
    public void dispatchRoundChangeEvent(RoundEvent evt) {
        for (RoundStateChangeEventHandler handler : roundStateChangeEventHandlerList) {
            handler.onRoundChangeEvent(evt);
        }
    }

    @Override
    public void dispatchPlayerChangeEvent(PlayerEvent evt) {
        for (PlayerStateChangeHandler handler : playerStateChangeHandlerList) {
            handler.onPlayerStateChange(evt);
        }
    }


}
