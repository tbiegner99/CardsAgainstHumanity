package com.tj.cardsagainsthumanity.server.protocol.impl.message.response;

import com.tj.cardsagainsthumanity.models.gameStatus.RoundStatus;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseResponse;

public class RoundStatusResponse extends BaseResponse<RoundStatus> {
    public RoundStatusResponse() {
        super(null, 204, "No Content");
    }

    public RoundStatusResponse(String messageId, RoundStatus responseData) {
        super(messageId, 200, "OK", responseData);
    }
}
