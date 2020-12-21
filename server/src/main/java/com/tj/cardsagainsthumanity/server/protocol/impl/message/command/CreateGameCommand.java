package com.tj.cardsagainsthumanity.server.protocol.impl.message.command;

import com.tj.cardsagainsthumanity.server.protocol.impl.ProtocolCommandName;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.CreateGameRequest;

public class CreateGameCommand extends BaseCommand<CreateGameRequest> {

    public CreateGameCommand() {
        super(ProtocolCommandName.CREATE_GAME, null);
    }

    public CreateGameCommand(CreateGameRequest request) {
        super(ProtocolCommandName.CREATE_GAME, request);
    }

}
