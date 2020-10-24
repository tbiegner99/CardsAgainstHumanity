package com.tj.cardsagainsthumanity.server.protocol.impl.message.command;

import com.tj.cardsagainsthumanity.server.protocol.impl.ProtocolCommandName;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.PlayerRequest;

public class CreateGameCommand extends BaseCommand<PlayerRequest> {

    public CreateGameCommand() {
        super(ProtocolCommandName.CREATE_GAME, null);
    }

    public CreateGameCommand(PlayerRequest request) {
        super(ProtocolCommandName.CREATE_GAME, request);
    }

}
