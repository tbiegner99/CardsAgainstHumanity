package com.tj.cardsagainsthumanity.server.protocol.impl.processor;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.models.gameStatus.GameStatus;
import com.tj.cardsagainsthumanity.models.gameStatus.GameStatusFactory;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.server.protocol.CommandProcessor;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseResponse;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.HostGameCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.body.PlayerResponseBody;
import com.tj.cardsagainsthumanity.server.protocol.message.CommandContext;
import com.tj.cardsagainsthumanity.services.gameplay.GameService;
import com.tj.cardsagainsthumanity.services.gameplay.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class HostGameCommandProcessor implements CommandProcessor<HostGameCommand, BaseResponse<GameStatus>> {

    private final GameStatusFactory gameStatusFactory;
    private GameService gameService;
    private PlayerService playerService;

    @Autowired
    public HostGameCommandProcessor(GameService gameService, PlayerService playerService, GameStatusFactory gameStatusFactory) {
        this.gameService = gameService;
        this.playerService = playerService;
        this.gameStatusFactory = gameStatusFactory;
    }

    private PlayerResponseBody mapPlayerToPlayerResponse(Player player) {
        return new PlayerResponseBody(player.getId(), player.getDisplayName(), player.getFirstName(), player.getLastName());
    }

    @Transactional()
    @Override
    public BaseResponse<GameStatus> processMessage(HostGameCommand messageToProcess, CommandContext context) {
        GameDriver driver = gameService.newGame();
        Player currentPlayer = context.getPlayer().orElseGet(null);
        context.joinGame(driver);
        driver.save();

        GameStatus body = gameStatusFactory.buildGameStatus(currentPlayer, driver);
        return new BaseResponse<>(messageToProcess.getMessageId(), 201, "Created", body);
    }
}
