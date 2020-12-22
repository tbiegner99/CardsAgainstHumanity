package com.tj.cardsagainsthumanity.client.options.factory;

import com.tj.cardsagainsthumanity.client.options.OptionContext;
import com.tj.cardsagainsthumanity.client.options.OptionSet;
import com.tj.cardsagainsthumanity.client.options.sets.EmptyOptionSet;
import com.tj.cardsagainsthumanity.client.options.sets.GameJoinedOptionSet;
import com.tj.cardsagainsthumanity.client.options.sets.LoggedInMenu;
import com.tj.cardsagainsthumanity.client.options.sets.LoginOptionSet;
import com.tj.cardsagainsthumanity.models.gameStatus.GameStatus;
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
        GameStatus state = context.getGameState();
        boolean isLoggedIn = context.getPlayer().isPresent();
        if (!isLoggedIn) {
            return getLoginOptions(state);
        } else {
            return optionsForLoggedInUser(context, state);
        }
    }

    private OptionSet getLoginOptions(GameStatus state) {
        return loginOptionSet;
    }

    private OptionSet optionsForLoggedInUser(OptionContext context, GameStatus state) {
        boolean isInGame = state.getGameId() != null;
        if (!isInGame) {
            return getJoinGameOptions(state);
        } else {
            switch (state.getState()) {
                case INITIALIZING:
                    return getWaitingForGameOptions(context, state);
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

    private OptionSet getGameplayOptions(GameStatus state) {
        return gameplayOptionsFactory.getOptionsFromGameState(state);
    }

    private OptionSet getWaitingForGameOptions(OptionContext context, GameStatus state) {
        if (context.isPlayerGameManager()) {
            return getGameManagementInitializationOptions(state);
        } else {
            return new EmptyOptionSet("Waiting for game to start");
        }
    }

    private OptionSet getGameManagementInitializationOptions(GameStatus state) {
        return gameInitalizationOptionSet;
    }

    private OptionSet getJoinGameOptions(GameStatus state) {
        return loggedInMenu;
    }
}
