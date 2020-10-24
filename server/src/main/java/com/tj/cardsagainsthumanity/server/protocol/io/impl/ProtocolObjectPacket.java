package com.tj.cardsagainsthumanity.server.protocol.io.impl;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseResponse;
import com.tj.cardsagainsthumanity.server.protocol.message.Command;
import com.tj.cardsagainsthumanity.server.protocol.message.Message;

@JsonDeserialize(using = MessageDeserializer.class)
public class ProtocolObjectPacket {

    private Message.Type messageType;
    private Message body;

    public ProtocolObjectPacket() {
    }

    public ProtocolObjectPacket(Message.Type messageType, Message body) {
        this();
        setMessageType(messageType);
        setBody(body);
    }

    public Message.Type getMessageType() {
        return messageType;
    }

    public void setMessageType(Message.Type messageType) {
        this.messageType = messageType;
    }

    public Message getBody() {
        return body;
    }

    public void setBody(Message bodyText) {
        this.body = bodyText;
    }
}