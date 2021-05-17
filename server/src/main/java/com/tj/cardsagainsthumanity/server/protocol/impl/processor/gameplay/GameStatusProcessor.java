package com.tj.cardsagainsthumanity.server.protocol.impl.processor.gameplay;

import com.tj.cardsagainsthumanity.dao.gameplay.GameDriverDao;
import com.tj.cardsagainsthumanity.models.gameStatus.GameStatus;
import com.tj.cardsagainsthumanity.models.gameStatus.GameStatusFactory;
import com.tj.cardsagainsthumanity.server.protocol.CommandProcessor;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.GameStatusCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.GameResponse;
import com.tj.cardsagainsthumanity.server.protocol.message.CommandContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

//Command for rejoining game on page refresh
@Component
public class GameStatusProcessor implements CommandProcessor<GameStatusCommand, GameResponse> {

    private final GameStatusFactory gameStatusFactory;
    private GameDriverDao gameDao;

    @Autowired
    public GameStatusProcessor(GameDriverDao gameDao, GameStatusFactory gameStatusFactory) {
        this.gameDao = gameDao;
        this.gameStatusFactory = gameStatusFactory;
    }

    @Transactional
    @Override
    public GameResponse processMessage(GameStatusCommand messageToProcess, CommandContext context) {
        if (!context.getCurrentGame().isPresent()) {
            throw new IllegalStateException("No curent game");
        }
        GameStatus status = gameStatusFactory.buildGameStatus(context.getPlayer().orElse(null), context.getCurrentGame().get());
        return new GameResponse(messageToProcess.getMessageId(), status);
    }

}
