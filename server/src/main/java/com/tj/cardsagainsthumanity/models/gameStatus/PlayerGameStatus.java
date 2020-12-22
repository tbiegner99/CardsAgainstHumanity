package com.tj.cardsagainsthumanity.models.gameStatus;

import com.tj.cardsagainsthumanity.models.gameplay.Game;
import com.tj.cardsagainsthumanity.serializer.responseModel.deck.DeckResponse;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundWhiteCard;

import java.util.List;
import java.util.Set;

public class PlayerGameStatus extends AudienceGameStatus {


    private final Set<RoundWhiteCard> handCards;

    public PlayerGameStatus(Integer gameId, String code, Game.GameState state, DeckResponse deck, RoundStatus roundStatus, List<PlayerInfo> players, Set<RoundWhiteCard> handCards) {
        super(gameId, code, state, deck, roundStatus, players);
        this.handCards = handCards;
    }

    public static PlayerGameStatus empty() {
        return new PlayerGameStatus(null, null, null, null, null, null, null);
    }


    public Set<RoundWhiteCard> getHandCards() {
        return handCards;
    }
}
