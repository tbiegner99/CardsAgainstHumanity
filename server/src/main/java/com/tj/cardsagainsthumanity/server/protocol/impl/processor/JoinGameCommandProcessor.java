package com.tj.cardsagainsthumanity.server.protocol.impl.processor;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.server.protocol.CommandProcessor;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseResponse;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.JoinGameCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.GameStatus;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundStatus;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundWhiteCard;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.body.ExtendedGameResponseBody;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.body.GameResponseBody;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.body.PlayerResponseBody;
import com.tj.cardsagainsthumanity.server.protocol.message.CommandContext;
import com.tj.cardsagainsthumanity.services.gameplay.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JoinGameCommandProcessor implements CommandProcessor<JoinGameCommand, BaseResponse<GameResponseBody>> {

    private GameService gameService;

    @Autowired
    public JoinGameCommandProcessor(GameService gameService) {
        this.gameService = gameService;
    }

    private PlayerResponseBody mapPlayerToPlayerResponse(Player player) {
        return new PlayerResponseBody(player.getId(), player.getDisplayName(), player.getFirstName(), player.getLastName());
    }

    @Override
    public BaseResponse<GameResponseBody> processMessage(JoinGameCommand messageToProcess, CommandContext context) {
        Player player = context.getPlayer().get();
        String gameCode = messageToProcess.getArguments().getCode();
        GameDriver driver = gameService.joinGame(player, gameCode);
        context.joinGame(driver);
        RoundStatus round = RoundStatus.fromRound(driver.getCurrentRound(), player);

        Set<RoundWhiteCard> handCards = GameStatus.getCardsForPlayer(driver, player);
        Collection<PlayerResponseBody> players = driver.getPlayers().stream().map(this::mapPlayerToPlayerResponse).collect(Collectors.toSet());
        GameResponseBody body = new ExtendedGameResponseBody(driver.getState(), driver.getCode(), driver.getGameId(), round, handCards, players);
        return new BaseResponse(messageToProcess.getMessageId(), 200, "OK", body);
    }
}
