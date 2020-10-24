package com.tj.cardsagainsthumanity.server.protocol.impl.processor;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.models.cards.WhiteCard;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.server.protocol.CommandProcessor;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseResponse;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.CreateGameCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundStatus;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundWhiteCard;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.body.ExtendedGameResponseBody;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.body.GameResponseBody;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.body.PlayerResponseBody;
import com.tj.cardsagainsthumanity.server.protocol.message.CommandContext;
import com.tj.cardsagainsthumanity.services.gameplay.GameService;
import com.tj.cardsagainsthumanity.services.gameplay.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CreateGameCommandProcessor implements CommandProcessor<CreateGameCommand, BaseResponse<GameResponseBody>> {

    private GameService gameService;
    private PlayerService playerService;

    @Autowired
    public CreateGameCommandProcessor(GameService gameService, PlayerService playerService) {
        this.gameService = gameService;
        this.playerService = playerService;
    }

    private PlayerResponseBody mapPlayerToPlayerResponse(Player player) {
        return new PlayerResponseBody(player.getId(), player.getDisplayName(), player.getFirstName(), player.getLastName());
    }

    @Transactional()
    @Override
    public BaseResponse<GameResponseBody> processMessage(CreateGameCommand messageToProcess, CommandContext context) {
        Player currentPlayer = context.getPlayer().get();
        GameDriver driver = gameService.newGame();
        gameService.joinGame(currentPlayer, driver.getCode());
        context.joinGame(driver);
        driver.save();

        List<PlayerResponseBody> players = driver.getPlayers().stream().map(this::mapPlayerToPlayerResponse).collect(Collectors.toList());
        Set<WhiteCard> handCards = driver.getGame().getPlayerHands().get(currentPlayer);
        Set<RoundWhiteCard> cards = handCards.stream().map(RoundWhiteCard::fromCard).collect(Collectors.toSet());
        RoundStatus roundStatus = RoundStatus.fromRound(driver.getCurrentRound(), currentPlayer);
        GameResponseBody body = new ExtendedGameResponseBody(driver.getState(), driver.getCode(), driver.getGameId(), roundStatus, cards, players);

        return new BaseResponse<>(messageToProcess.getMessageId(), 201, "Created", body);
    }
}
