package com.tj.cardsagainsthumanity.server.protocol.impl.message.command;

import com.tj.cardsagainsthumanity.models.gameStatus.RoundStatus;
import com.tj.cardsagainsthumanity.server.protocol.impl.ProtocolCommandName;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseCommand;

public class RoundStatusCommand extends BaseCommand<RoundStatus> {

    public RoundStatusCommand() {
        this(null);
    }

    public RoundStatusCommand(RoundStatus body) {
        super(ProtocolCommandName.ROUND_STATUS, body);
    }
}
