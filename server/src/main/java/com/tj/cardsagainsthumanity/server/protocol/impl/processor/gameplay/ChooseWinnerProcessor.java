package com.tj.cardsagainsthumanity.server.protocol.impl.processor.gameplay;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.core.game.RoundDriver;
import com.tj.cardsagainsthumanity.models.gameStatus.GameStatus;
import com.tj.cardsagainsthumanity.models.gameStatus.GameStatusFactory;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.server.protocol.CommandProcessor;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundCardPlay;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.gameplay.ChooseWinnerCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.GameResponse;
import com.tj.cardsagainsthumanity.server.protocol.message.CommandContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class ChooseWinnerProcessor implements CommandProcessor<ChooseWinnerCommand, GameResponse> {

    private final GameStatusFactory gameStatusFactory;

    @Autowired
    public ChooseWinnerProcessor(GameStatusFactory gameStatusFactory) {
        this.gameStatusFactory = gameStatusFactory;
    }

    @Transactional
    @Override
    public GameResponse processMessage(ChooseWinnerCommand messageToProcess, CommandContext context) {
        RoundCardPlay winningPlay = messageToProcess.getArguments();
        Player currentPlayer = context.getPlayer().get();
        GameDriver game = context.getCurrentGame().get();
        RoundDriver currentRound = game.getCurrentRound();
        currentRound.declareWinner(currentPlayer, winningPlay.getId());

        GameStatus body = gameStatusFactory.buildGameStatus(currentPlayer, game);
        return new GameResponse(messageToProcess.getMessageId(), body);
    }
}
