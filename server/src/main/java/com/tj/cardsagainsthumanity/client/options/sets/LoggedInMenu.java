package com.tj.cardsagainsthumanity.client.options.sets;

import com.tj.cardsagainsthumanity.client.options.types.common.ExitOption;
import com.tj.cardsagainsthumanity.client.options.types.gameManagement.CreateGameOption;
import com.tj.cardsagainsthumanity.client.options.types.gameManagement.JoinGameOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoggedInMenu extends BaseOptionSet {

    @Autowired
    public LoggedInMenu(JoinGameOption joinGame, CreateGameOption createGame, ExitOption exit) {
        super(createGame, joinGame, exit);

    }

}
