package com.tj.cardsagainsthumanity.server.protocol.impl.message.response.body;


import com.tj.cardsagainsthumanity.models.gameplay.Game.GameState;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.GameStatus;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundStatus;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundWhiteCard;

import java.util.Set;

public class GameResponseBody extends GameStatus {

    private String code;


    public GameResponseBody() {

    }

    public GameResponseBody(Integer gameId, String code, GameState state, RoundStatus roundStatus, Set<RoundWhiteCard> handCards) {
        super(gameId, state, roundStatus, handCards);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}