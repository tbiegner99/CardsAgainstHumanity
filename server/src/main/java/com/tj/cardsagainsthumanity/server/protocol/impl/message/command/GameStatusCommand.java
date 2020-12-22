package com.tj.cardsagainsthumanity.server.protocol.impl.message.command;

import com.tj.cardsagainsthumanity.models.gameStatus.GameStatus;
import com.tj.cardsagainsthumanity.server.protocol.impl.ProtocolCommandName;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseCommand;

public class GameStatusCommand extends BaseCommand<GameStatus> {

    public GameStatusCommand() {
        this(null);
    }

    public GameStatusCommand(GameStatus body) {
        super(ProtocolCommandName.GAME_STATUS, body);
    }
}
