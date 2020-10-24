package com.tj.cardsagainsthumanity.server.protocol.impl.message.command.gameplay;

import com.tj.cardsagainsthumanity.server.protocol.impl.ProtocolCommandName;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.CardPlayRequest;

public class PlayCardCommand extends BaseCommand<CardPlayRequest> {

    public PlayCardCommand() {
        this(null);
    }

    public PlayCardCommand(CardPlayRequest request) {
        super(ProtocolCommandName.PLAY, request);
    }
}
