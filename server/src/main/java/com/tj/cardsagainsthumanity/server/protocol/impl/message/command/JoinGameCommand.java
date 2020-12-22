package com.tj.cardsagainsthumanity.server.protocol.impl.message.command;

import com.tj.cardsagainsthumanity.server.protocol.impl.ProtocolCommandName;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.JoinGameRequest;

public class JoinGameCommand extends BaseCommand<JoinGameRequest> {

    public JoinGameCommand() {
        super(ProtocolCommandName.JOIN, null);
    }

    public JoinGameCommand(JoinGameRequest arguments) {
        super(ProtocolCommandName.JOIN, arguments);
    }

    @Override
    public boolean isLoginRequired() {
        return false;
    }
}
