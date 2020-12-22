package com.tj.cardsagainsthumanity.server.protocol.impl.processor.gameplay;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.core.game.RoundDriver;
import com.tj.cardsagainsthumanity.models.gameStatus.GameStatus;
import com.tj.cardsagainsthumanity.models.gameStatus.GameStatusFactory;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.server.protocol.CommandProcessor;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.gameplay.EndRoundCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.GameResponse;
import com.tj.cardsagainsthumanity.server.protocol.message.CommandContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class EndRoundProcessor implements CommandProcessor<EndRoundCommand, GameResponse> {

    private final GameStatusFactory gameStatusFactory;

    @Autowired
    public EndRoundProcessor(GameStatusFactory gameStatusFactory) {
        this.gameStatusFactory = gameStatusFactory;
    }

    @Transactional
    @Override
    public GameResponse processMessage(EndRoundCommand messageToProcess, CommandContext context) {
        Player currentPlayer = context.getPlayer().get();
        GameDriver game = context.getCurrentGame().get();
        RoundDriver currentRound = game.getCurrentRound();
        currentRound.endRound();

        GameStatus body = gameStatusFactory.buildGameStatus(currentPlayer, game);
        return new GameResponse(messageToProcess.getMessageId(), body);
    }
}
