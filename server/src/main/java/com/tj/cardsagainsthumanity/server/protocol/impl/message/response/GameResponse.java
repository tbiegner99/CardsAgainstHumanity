package com.tj.cardsagainsthumanity.server.protocol.impl.message.response;

import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseResponse;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.body.GameResponseBody;

public class GameResponse extends BaseResponse<GameResponseBody> {
    public GameResponse() {
    }

    public GameResponse(String messageId, int status, String statusMessage, GameResponseBody body) {
        super(messageId, status, statusMessage, body);
    }
}
