package com.tj.cardsagainsthumanity.server.protocol.impl.message.response;

import com.tj.cardsagainsthumanity.models.gameStatus.GameStatus;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseResponse;

public class GameResponse extends BaseResponse<GameStatus> {
    public GameResponse() {
    }

    public GameResponse(String messageId, int status, String statusMessage, GameStatus body) {
        super(messageId, status, statusMessage, body);
    }

    public GameResponse(String messageId, GameStatus body) {
        super(messageId, 200, "OK", body);
    }
}
