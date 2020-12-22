package com.tj.cardsagainsthumanity.server.protocol.impl.message.response;

import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseResponse;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.body.LoginResponseBody;

public class SuccessfulLoginResponse extends BaseResponse<LoginResponseBody> {

    public SuccessfulLoginResponse() {
        super(null, 204, "No Content");
    }

    public SuccessfulLoginResponse(String messageId, LoginResponseBody body) {
        super(messageId, 200, "OK", body);
    }

}
