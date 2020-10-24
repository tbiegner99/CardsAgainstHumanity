package com.tj.cardsagainsthumanity.server.protocol.impl.message.response.body;

import com.tj.cardsagainsthumanity.models.gameplay.Game;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundStatus;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundWhiteCard;

import java.util.Collection;
import java.util.Set;

public class ExtendedGameResponseBody extends GameResponseBody {
    private Collection<PlayerResponseBody> players;

    public ExtendedGameResponseBody(Game.GameState state, String code, Integer gameId, RoundStatus roundStatus, Set<RoundWhiteCard> handCards, Collection<PlayerResponseBody> players) {
        super(gameId, code, state, roundStatus, handCards);
        this.players = players;
    }

    public ExtendedGameResponseBody() {

    }

    public Collection<PlayerResponseBody> getPlayers() {
        return players;
    }

    public void setPlayers(Collection<PlayerResponseBody> players) {
        this.players = players;
    }
}
