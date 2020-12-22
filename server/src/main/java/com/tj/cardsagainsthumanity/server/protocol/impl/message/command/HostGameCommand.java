package com.tj.cardsagainsthumanity.server.protocol.impl.message.command;

import com.tj.cardsagainsthumanity.server.protocol.impl.ProtocolCommandName;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.PlayerRequest;

public class HostGameCommand extends BaseCommand<PlayerRequest> {

    public HostGameCommand() {
        super(ProtocolCommandName.HOST_GAME, null);
    }

    public HostGameCommand(PlayerRequest request) {
        super(ProtocolCommandName.CREATE_GAME, request);
    }

}
