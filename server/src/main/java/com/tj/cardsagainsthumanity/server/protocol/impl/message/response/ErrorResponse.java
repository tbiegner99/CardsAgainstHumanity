package com.tj.cardsagainsthumanity.server.protocol.impl.message.response;

import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseResponse;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.body.ErrorMessage;
import com.tj.cardsagainsthumanity.server.protocol.message.Response;
import org.springframework.http.HttpStatus;

public class ErrorResponse {

    private String messageId;

    private ErrorResponse(String messageId) {
        this.messageId = messageId;
    }

    public static ErrorResponse forMessageId(String messageId) {
        return new ErrorResponse(messageId);
    }

    private BaseResponse<ErrorMessage> createResponse(HttpStatus status, String message, String cause) {
        ErrorMessage body = new ErrorMessage(message, cause);
        return new BaseResponse<>(messageId, status.value(), status.getReasonPhrase(), body);
    }

    public BaseResponse<ErrorMessage> forbidden(String message, String cause) {
        return createResponse(HttpStatus.FORBIDDEN, message, cause);
    }

    public BaseResponse<ErrorMessage> forbidden(String message) {
        return forbidden(message, null);
    }

    public Response unauthorized(String message, String cause) {
        return createResponse(HttpStatus.UNAUTHORIZED, message, cause);
    }

    public Response unauthorized(String message) {
        return unauthorized(message, null);
    }
}
