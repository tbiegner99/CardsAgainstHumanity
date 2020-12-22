package com.tj.cardsagainsthumanity.server.protocol.impl.message.command;

import com.tj.cardsagainsthumanity.server.protocol.impl.ProtocolCommandName;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.GameRequest;

public class StartGameCommand extends BaseCommand<GameRequest> {

    public StartGameCommand() {
        super(ProtocolCommandName.START_GAME, null);
    }

    public StartGameCommand(GameRequest arguments) {
        super(ProtocolCommandName.START_GAME, arguments);
    }


}
