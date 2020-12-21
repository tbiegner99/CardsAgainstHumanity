package com.tj.cardsagainsthumanity.models.gameStatus;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.models.cards.DeckInfo;
import com.tj.cardsagainsthumanity.models.gameplay.Game;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.serializer.responseModel.deck.DeckResponse;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundWhiteCard;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Component
public class GameStatusFactory {


    public GameStatus buildGameStatus(Player currentPlayer, GameDriver game) {
        if (currentPlayer == null) {
            return buildAudienceGameStatus(currentPlayer, game);
        } else {
            return buildGamePlayerStatus(currentPlayer, game);
        }
    }

    private List<PlayerInfo> getPlayerInfo(Player currentPlayer, GameDriver game) {
        return game.getPlayers()
                .stream()
                .map(player -> PlayerInfo.fromPlayer(game, player))
                .collect(Collectors.toList());
    }

    private Set<RoundWhiteCard> getCardsForPlayer(Player currentPlayer, GameDriver driver) {
        Game game = driver.getGame();
        return game.getPlayerHands().get(currentPlayer)
                .stream()
                .map(card -> new RoundWhiteCard(card.getId(), card.getText()))
                .collect(Collectors.toSet());
    }

    private PlayerGameStatus buildGamePlayerStatus(Player currentPlayer, GameDriver game) {
        RoundStatus roundStatus = RoundStatus.fromRound(game.getCurrentRound(), currentPlayer);
        List<PlayerInfo> players = getPlayerInfo(currentPlayer, game);
        Set<RoundWhiteCard> handCards = getCardsForPlayer(currentPlayer, game);
        DeckResponse deck = getDeckInfo(game.getGame());
        return new PlayerGameStatus(game.getGameId(), game.getCode(), game.getState(), deck, roundStatus, players, handCards);
    }

    private DeckResponse getDeckInfo(Game game) {
        DeckInfo deck = game.getDeck();
        DeckResponse ret = new DeckResponse();
        ret.setId(deck.getDeckId());
        ret.setName(deck.getName());
        return ret;
    }

    private GameStatus buildAudienceGameStatus(Player currentPlayer, GameDriver game) {
        RoundStatus roundStatus = RoundStatus.fromRound(game.getCurrentRound(), currentPlayer);
        List<PlayerInfo> players = getPlayerInfo(currentPlayer, game);
        DeckResponse deck = getDeckInfo(game.getGame());
        return new AudienceGameStatus(game.getGameId(), game.getCode(), game.getState(), deck, roundStatus, players);
    }

}
