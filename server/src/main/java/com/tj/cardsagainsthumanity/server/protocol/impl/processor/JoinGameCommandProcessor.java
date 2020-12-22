package com.tj.cardsagainsthumanity.server.protocol.impl.processor;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.models.gameStatus.GameStatus;
import com.tj.cardsagainsthumanity.models.gameStatus.GameStatusFactory;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.server.protocol.CommandProcessor;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseResponse;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.JoinGameCommand;
import com.tj.cardsagainsthumanity.server.protocol.message.CommandContext;
import com.tj.cardsagainsthumanity.services.gameplay.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JoinGameCommandProcessor implements CommandProcessor<JoinGameCommand, BaseResponse<GameStatus>> {

    private final GameStatusFactory gameStatusFactory;
    private GameService gameService;

    @Autowired
    public JoinGameCommandProcessor(GameService gameService, GameStatusFactory gameStatusFactory) {
        this.gameService = gameService;
        this.gameStatusFactory = gameStatusFactory;
    }

    @Override
    public BaseResponse<GameStatus> processMessage(JoinGameCommand messageToProcess, CommandContext context) {

        String gameCode = messageToProcess.getArguments().getCode();
        Player player = null;
        GameDriver driver;
        if (context.getPlayer().isPresent()) {
            player = context.getPlayer().get();
            driver = gameService.joinGame(player, gameCode);
        } else {
            driver = gameService.loadGame(gameCode);
        }
        context.joinGame(driver);
        GameStatus body = this.gameStatusFactory.buildGameStatus(player, driver);
        return new BaseResponse(messageToProcess.getMessageId(), 200, "OK", body);
    }
}
