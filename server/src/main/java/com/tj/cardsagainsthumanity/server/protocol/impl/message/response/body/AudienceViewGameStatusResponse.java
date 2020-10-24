package com.tj.cardsagainsthumanity.server.protocol.impl.message.response.body;

import com.tj.cardsagainsthumanity.models.gameplay.Game;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundStatus;

import java.util.Collection;

public class AudienceViewGameStatusResponse {
    private Integer gameId;
    private Game.GameState state;
    private RoundStatus round;
    private Collection<GamePlayerResponseBody> players;

    public AudienceViewGameStatusResponse(Integer gameId, Game.GameState state, RoundStatus roundStatus, Collection<GamePlayerResponseBody> players) {

    }

}
