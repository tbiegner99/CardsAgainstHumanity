package com.tj.cardsagainsthumanity.server.protocol.impl.processor;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.models.gameStatus.GameStatus;
import com.tj.cardsagainsthumanity.models.gameStatus.GameStatusFactory;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.server.protocol.CommandProcessor;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseResponse;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.CreateGameCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.body.PlayerResponseBody;
import com.tj.cardsagainsthumanity.server.protocol.message.CommandContext;
import com.tj.cardsagainsthumanity.services.gameplay.GameService;
import com.tj.cardsagainsthumanity.services.gameplay.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class CreateGameCommandProcessor implements CommandProcessor<CreateGameCommand, BaseResponse<GameStatus>> {

    private final GameStatusFactory gameStatusFactory;
    private GameService gameService;
    private PlayerService playerService;

    @Autowired
    public CreateGameCommandProcessor(GameService gameService, PlayerService playerService, GameStatusFactory gameStatusFactory) {
        this.gameService = gameService;
        this.playerService = playerService;
        this.gameStatusFactory = gameStatusFactory;
    }

    private PlayerResponseBody mapPlayerToPlayerResponse(Player player) {
        return new PlayerResponseBody(player.getId(), player.getDisplayName(), player.getFirstName(), player.getLastName());
    }

    @Transactional()
    @Override
    public BaseResponse<GameStatus> processMessage(CreateGameCommand messageToProcess, CommandContext context) {
        Player currentPlayer = context.getPlayer().orElseGet(null);
        GameDriver driver = gameService.newGame(messageToProcess.getArguments().getDeckId());
        if (currentPlayer != null) {
            gameService.joinGame(currentPlayer, driver.getCode());
        }
        context.joinGame(driver);
        driver.save();

        GameStatus body = gameStatusFactory.buildGameStatus(currentPlayer, driver);

        return new BaseResponse<>(messageToProcess.getMessageId(), 201, "Created", body);
    }
}
