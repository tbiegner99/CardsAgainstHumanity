package com.tj.cardsagainsthumanity.models.gameStatus;


import com.tj.cardsagainsthumanity.models.gameplay.Game;
import com.tj.cardsagainsthumanity.serializer.responseModel.deck.DeckResponse;

import java.util.List;

public class AudienceGameStatus extends GameStatus {

    public AudienceGameStatus(Integer gameId, String code, Game.GameState state, DeckResponse deck, RoundStatus roundStatus, List<PlayerInfo> players) {
        super(gameId, code, state, deck, roundStatus, players);
    }
}
