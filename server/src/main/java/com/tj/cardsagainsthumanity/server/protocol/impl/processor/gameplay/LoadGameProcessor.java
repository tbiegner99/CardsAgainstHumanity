package com.tj.cardsagainsthumanity.server.protocol.impl.processor.gameplay;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.dao.gameplay.GameDriverDao;
import com.tj.cardsagainsthumanity.models.gameStatus.GameStatus;
import com.tj.cardsagainsthumanity.models.gameStatus.GameStatusFactory;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.server.protocol.CommandProcessor;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.LoadGameCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.GameRequest;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.GameResponse;
import com.tj.cardsagainsthumanity.server.protocol.message.CommandContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

//Command for rejoining game on page refresh
@Component
public class LoadGameProcessor implements CommandProcessor<LoadGameCommand, GameResponse> {

    private final GameStatusFactory gameStatusFactory;
    private GameDriverDao gameDao;

    @Autowired
    public LoadGameProcessor(GameDriverDao gameDao, GameStatusFactory gameStatusFactory) {
        this.gameDao = gameDao;
        this.gameStatusFactory = gameStatusFactory;
    }

    @Transactional
    @Override
    public GameResponse processMessage(LoadGameCommand messageToProcess, CommandContext context) {
        GameRequest gameRequest = messageToProcess.getArguments();
        Player currentPlayer = context.getPlayer().orElse(null);
        GameDriver game = getGame(gameRequest);
        if (!game.isPlayerInGame(currentPlayer)) {
            throw new IllegalStateException("Player is not in game");
        }
        GameStatus status = gameStatusFactory.buildGameStatus(currentPlayer, game);
        return new GameResponse(messageToProcess.getMessageId(), status);
    }

    private GameDriver getGame(GameRequest gameRequest) {
        if (gameRequest.getGameId() != null) {
            return gameDao.getGame(gameRequest.getGameId());
        } else if (gameRequest.getGameCode() != null) {
            return gameDao.getGameByCode(gameRequest.getGameCode());
        } else {
            throw new IllegalArgumentException(("Code or game id must be supplied"));
        }
    }
}
