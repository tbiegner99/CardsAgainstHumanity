package com.tj.cardsagainsthumanity.server.protocol.io.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseResponse;
import com.tj.cardsagainsthumanity.server.protocol.message.Message;

import java.io.IOException;

public class MessageDeserializer extends StdDeserializer<ProtocolPacket> {

    protected MessageDeserializer() {
        this(null);
    }


    protected MessageDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ProtocolPacket deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        ObjectMapper mapper =new ObjectMapper();
        String messageType = node.get("messageType").asText();
        Class<?> targetClass = null;
        Message.Type type = null;
        if(Message.Type.RESPONSE.name().equals(messageType)) {
            targetClass= BaseResponse.class;
            type=Message.Type.RESPONSE;
        } else if(Message.Type.COMMAND.name().equals(messageType)) {
            targetClass=BaseCommand.class;
            type=Message.Type.COMMAND;
        } else {
            throw new IllegalArgumentException(messageType);
        }
        Message message = (Message)mapper.treeToValue(node.get("body"),targetClass);
        ProtocolPacket packet = new ProtocolPacket();
        packet.setBody(message);
        packet.setMessageType(type);
        return packet;
    }
}
