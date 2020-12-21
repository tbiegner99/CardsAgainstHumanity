package com.tj.cardsagainsthumanity.server.protocol.impl.message.command.gameplay;

import com.tj.cardsagainsthumanity.server.protocol.impl.ProtocolCommandName;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.EmptyRequest;

public class RevealPlayCommand extends BaseCommand<EmptyRequest> {

    public RevealPlayCommand() {
        this(null);
    }

    public RevealPlayCommand(EmptyRequest request) {
        super(ProtocolCommandName.REVEAL, request);
    }
}
