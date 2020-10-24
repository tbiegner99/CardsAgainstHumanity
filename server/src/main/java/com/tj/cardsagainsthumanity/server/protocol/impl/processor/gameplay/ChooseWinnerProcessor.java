package com.tj.cardsagainsthumanity.server.protocol.impl.processor.gameplay;

import com.tj.cardsagainsthumanity.core.game.RoundDriver;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.server.protocol.CommandProcessor;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundCardPlay;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundStatus;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.gameplay.ChooseWinnerCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.RoundStatusResponse;
import com.tj.cardsagainsthumanity.server.protocol.message.CommandContext;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class ChooseWinnerProcessor implements CommandProcessor<ChooseWinnerCommand, RoundStatusResponse> {

    @Transactional
    @Override
    public RoundStatusResponse processMessage(ChooseWinnerCommand messageToProcess, CommandContext context) {
        RoundCardPlay winningPlay = messageToProcess.getArguments();
        Player currentPlayer = context.getPlayer().get();
        RoundDriver currentRound = context.getCurrentGame().get().getCurrentRound();
        currentRound.declareWinner(currentPlayer, winningPlay.getId());

        RoundStatus responseData = RoundStatus.fromRound(currentRound, currentPlayer);
        return new RoundStatusResponse(messageToProcess.getMessageId(), responseData);
    }
}
