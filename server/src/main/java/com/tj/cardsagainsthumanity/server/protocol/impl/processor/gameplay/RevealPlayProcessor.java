package com.tj.cardsagainsthumanity.server.protocol.impl.processor.gameplay;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.models.gameStatus.GameStatus;
import com.tj.cardsagainsthumanity.models.gameStatus.GameStatusFactory;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.server.protocol.CommandProcessor;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.gameplay.RevealPlayCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.EmptyResponse;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.ErrorResponse;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.GameResponse;
import com.tj.cardsagainsthumanity.server.protocol.message.CommandContext;
import com.tj.cardsagainsthumanity.server.protocol.message.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Component
public class RevealPlayProcessor implements CommandProcessor<RevealPlayCommand, Response<?>> {
    private GameStatusFactory statusFactory;

    @Autowired
    public RevealPlayProcessor(GameStatusFactory statusFactory) {
        this.statusFactory = statusFactory;
    }

    @Override
    @Transactional
    public Response<?> processMessage(RevealPlayCommand messageToProcess, CommandContext context) {
        try {
            GameDriver game = context.getCurrentGame().get();
            Player player = context.getPlayer().get();
            game.getCurrentRound().revealPlay(player);
            GameStatus status = statusFactory.buildGameStatus(player, game);
            return new GameResponse(messageToProcess.getMessageId(), status);
        } catch (IllegalStateException e) {
            return ErrorResponse.forMessageId(messageToProcess.getMessageId())
                    .forbidden("You must be the current czar to perform this action");
        } catch (NoSuchElementException ex) {
            return EmptyResponse.NOT_FOUND.forMessage(messageToProcess.getMessageId());
        }
    }
}
