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
    public void unregisterGameStartedHandler(GameStartedEventHandler handler) {
        gameStartedEventHandlerList.remove(handler);
    }

    @Override
    public void unregisterGameOverHandler(GameOverEventHandler handler) {
        gameOverEventHandlerList.remove(handler);
    }

    @Override
    public void unregisterGameStateChangeHandler(GameStateChangeEventHandler handler) {
        gameStateChangeEventHandlerList.remove(handler);
    }

    @Override
    public void unregisterRoundStartedHandler(RoundStartedEventHandler handler) {
        roundStartedEventHandlerList.remove(handler);
    }

    @Override
    public void unregisterRoundOverHandler(RoundOverEventHandler handler) {
        roundOverEventHandlerList.remove(handler);
    }

    @Override
    public void unregisterRoundStateChangeHandler(RoundStateChangeEventHandler handler) {
        roundStateChangeEventHandlerList.remove(handler);
    }

    @Override
    public void unregisterPlayerStateChangeEvent(PlayerStateChangeHandler handler) {
        playerStateChangeHandlerList.remove(handler);
    }

    private void logException(Exception e) {
    }

    @Override
    public void dispatchGameStartedEvent(GameEvent evt) {
        for (GameStartedEventHandler handler : gameStartedEventHandlerList) {
            try {
                handler.onGameStarted(evt);
            } catch (Exception e) {
                logException(e);
            }
        }
    }


    @Override
    public void dispatchGameOverEvent(GameEvent evt) {
        for (GameOverEventHandler handler : gameOverEventHandlerList) {
            try {
                handler.onGameOver(evt);
            } catch (Exception e) {
                logException(e);
            }
        }
    }

    @Override
    public void dispatchGameChangeEvent(GameEvent evt) {
        for (GameStateChangeEventHandler handler : gameStateChangeEventHandlerList) {
            try {
                handler.onGameStateChange(evt);
            } catch (Exception e) {
                logException(e);
            }
        }
    }

    @Override
    public void dispatchRoundStartedEvent(RoundEvent evt) {
        for (RoundStartedEventHandler handler : roundStartedEventHandlerList) {
            try {
                handler.onRoundStart(evt);
            } catch (Exception e) {
                logException(e);
            }
        }
    }

    @Override
    public void dispatchRoundOverEvent(RoundEvent evt) {
        for (RoundOverEventHandler handler : roundOverEventHandlerList) {
            try {
                handler.onRoundOver(evt);
            } catch (Exception e) {
                logException(e);
            }
        }
    }

    @Override
    public void dispatchRoundChangeEvent(RoundEvent evt) {
        for (RoundStateChangeEventHandler handler : roundStateChangeEventHandlerList) {
            try {
                handler.onRoundChangeEvent(evt);
            } catch (Exception e) {
                logException(e);
            }
        }
    }

    @Override
    public void dispatchPlayerChangeEvent(PlayerEvent evt) {
        for (PlayerStateChangeHandler handler : playerStateChangeHandlerList) {
            try {
                handler.onPlayerStateChange(evt);
            } catch (Exception e) {
                logException(e);
            }
        }
    }


}
