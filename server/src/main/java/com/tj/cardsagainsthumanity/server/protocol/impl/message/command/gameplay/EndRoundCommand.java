package com.tj.cardsagainsthumanity.server.protocol.impl.message.command.gameplay;

import com.tj.cardsagainsthumanity.server.protocol.impl.ProtocolCommandName;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.EmptyRequest;

public class EndRoundCommand extends BaseCommand<EmptyRequest> {
    public EndRoundCommand() {
        super(ProtocolCommandName.END_ROUND, null);
    }

    public EndRoundCommand(EmptyRequest request) {
        super(ProtocolCommandName.END_ROUND, request);
    }

}
