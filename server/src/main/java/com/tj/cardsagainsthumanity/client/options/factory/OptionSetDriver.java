package com.tj.cardsagainsthumanity.client.options.factory;

import com.tj.cardsagainsthumanity.client.model.GameState;
import com.tj.cardsagainsthumanity.client.options.OptionContext;
import com.tj.cardsagainsthumanity.client.options.OptionSet;
import com.tj.cardsagainsthumanity.client.options.sets.EmptyOptionSet;
import com.tj.cardsagainsthumanity.client.options.sets.GameJoinedOptionSet;
import com.tj.cardsagainsthumanity.client.options.sets.LoggedInMenu;
import com.tj.cardsagainsthumanity.client.options.sets.LoginOptionSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OptionSetDriver {
    private LoginOptionSet loginOptionSet;
    private LoggedInMenu loggedInMenu;
    private GameplayOptionsFactory gameplayOptionsFactory;
    private GameJoinedOptionSet gameInitalizationOptionSet;

    @Autowired
    public OptionSetDriver(LoginOptionSet loginOptionSet, LoggedInMenu loggedInMenu, GameplayOptionsFactory gameplayOptionsFactory, GameJoinedOptionSet gameInitalizationOptionSet) {
        this.loginOptionSet = loginOptionSet;
        this.loggedInMenu = loggedInMenu;
        this.gameplayOptionsFactory = gameplayOptionsFactory;
        this.gameInitalizationOptionSet = gameInitalizationOptionSet;
    }

    public OptionSet getOptionsFromContext(OptionContext context) {
        GameState state = context.getGameState();
        boolean isLoggedIn = state.getCurrentPlayerId().isPresent();
        if (!isLoggedIn) {
            return getLoginOptions(state);
        } else {
            return optionsForLoggedInUser(state);
        }
    }

    private OptionSet getLoginOptions(GameState state) {
        return loginOptionSet;
    }

    private OptionSet optionsForLoggedInUser(GameState state) {
        boolean isInGame = state.getCurrentGameId().isPresent();
        if (!isInGame) {
            return getJoinGameOptions(state);
        } else {
            switch (state.getState().get()) {
                case INITIALIZING:
                    return getWaitingForGameOptions(state);
                case STARTED:
                    return getGameplayOptions(state);
                case OVER:
                default:
                    return gameOverOptions();
            }
        }
    }

    private OptionSet gameOverOptions() {
        return new EmptyOptionSet("Game is over...");
    }

    private OptionSet getGameplayOptions(GameState state) {
        return gameplayOptionsFactory.getOptionsFromGameState(state);
    }

    private OptionSet getWaitingForGameOptions(GameState state) {
        if (state.isPlayerGameManager()) {
            return getGameManagementInitializationOptions(state);
        } else {
            return new EmptyOptionSet("Waiting for game to start");
        }
    }

    private OptionSet getGameManagementInitializationOptions(GameState state) {
        return gameInitalizationOptionSet;
    }

    private OptionSet getJoinGameOptions(GameState state) {
        return loggedInMenu;
    }
}
