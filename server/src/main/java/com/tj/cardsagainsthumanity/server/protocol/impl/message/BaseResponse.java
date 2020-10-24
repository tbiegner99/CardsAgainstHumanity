package com.tj.cardsagainsthumanity.server.protocol.impl.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tj.cardsagainsthumanity.server.protocol.message.Message;
import com.tj.cardsagainsthumanity.server.protocol.message.Response;

import java.util.Objects;

public class BaseResponse<T> implements Response<T> {
    private int status;
    private String statusMessage;
    private String messageId;
    private T body;

    public BaseResponse() {
    }

    public BaseResponse(String responseId, int status, String statusMessage) {
        this(responseId, status, statusMessage, null);
    }

    public BaseResponse(String responseId, int status, String statusMessage, T body) {
        setMessageId(responseId);
        setStatus(status);
        setStatusMessage(statusMessage);
        setBody(body);
    }

    @Override
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    @Override
    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    @Override
    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Override
    @JsonIgnore
    public Message.Type getMessageType() {
        return Type.RESPONSE;
    }

    @Override
    @JsonIgnore
    public boolean isErrorResponse() {
        return getStatus() < 200 || getStatus() >= 300;
    }

    @Override
    public boolean equals(Object o) {
        BaseResponse<?> that = (BaseResponse<?>) o;
        return getStatus() == that.getStatus() &&
                Objects.equals(getStatusMessage(), that.getStatusMessage()) &&
                Objects.equals(getMessageId(), that.getMessageId()) &&
                Objects.equals(getBody(), that.getBody());
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "status=" + status +
                ", statusMessage='" + statusMessage + '\'' +
                ", messageId='" + messageId + '\'' +
                ", body=" + body +
                '}';
    }
}
