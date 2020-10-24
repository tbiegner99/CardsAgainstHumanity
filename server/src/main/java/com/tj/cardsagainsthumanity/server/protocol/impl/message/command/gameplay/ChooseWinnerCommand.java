package com.tj.cardsagainsthumanity.server.protocol.impl.message.command.gameplay;

import com.tj.cardsagainsthumanity.server.protocol.impl.ProtocolCommandName;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundCardPlay;

public class ChooseWinnerCommand extends BaseCommand<RoundCardPlay> {
    public ChooseWinnerCommand() {
        super(ProtocolCommandName.CHOOSE_WINNER, null);
    }

    public ChooseWinnerCommand(RoundCardPlay request) {
        super(ProtocolCommandName.CHOOSE_WINNER, request);
    }

}
