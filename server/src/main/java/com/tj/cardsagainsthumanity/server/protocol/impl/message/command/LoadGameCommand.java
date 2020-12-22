package com.tj.cardsagainsthumanity.server.protocol.impl.message.command;

import com.tj.cardsagainsthumanity.server.protocol.impl.ProtocolCommandName;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.GameRequest;

public class LoadGameCommand extends BaseCommand<GameRequest> {

    public LoadGameCommand() {
        this(null);
    }

    public LoadGameCommand(GameRequest body) {
        super(ProtocolCommandName.LOAD_GAME, body);
    }

    @Override
    public boolean isLoginRequired() {
        return false;
    }
}
