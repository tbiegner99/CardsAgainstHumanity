package com.tj.cardsagainsthumanity.server.protocol.impl.message.response;

import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseResponse;

public class EmptyResponse extends BaseResponse {

    public static final EmptyResponse NO_CONTENT = new EmptyResponse(null, 204, "No Content");
    public static final EmptyResponse FORBIDDEN = new EmptyResponse(null, 403, "Forbidden");
    public static final EmptyResponse METHOD_NOT_FOUND = new EmptyResponse(null, 405, "Method Not Found");
    public static final EmptyResponse INTERNAL_SERVER_ERROR = new EmptyResponse(null, 500, "Internal Server Error");

    public EmptyResponse(String messageId, int statusCode, String statusMessage) {
        super(messageId, statusCode, statusMessage);
    }

    public EmptyResponse forMessage(String messageId) {
        return new EmptyResponse(messageId, this.getStatus(), this.getStatusMessage());
    }
}
