package com.tj.cardsagainsthumanity.server.protocol.io.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseResponse;
import com.tj.cardsagainsthumanity.server.protocol.io.MessageSerializer;
import com.tj.cardsagainsthumanity.server.protocol.message.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JSONSerializer implements MessageSerializer {

    ObjectMapper createObjectMapper() {
        return new ObjectMapper();
    }

    @Override
    public <T extends Message> T deserializeMessage(String rawData, Class<T> expectedType) {
        try {
            ObjectMapper mapper = createObjectMapper();
            ProtocolPacket packet = mapper.readValue(rawData, ProtocolPacket.class);
            return expectedType.cast(packet.getBody());
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Message deserializeMessage(String rawData) {
        try {
            ObjectMapper mapper = createObjectMapper();
            ProtocolPacket packet = mapper.readValue(rawData, ProtocolPacket.class);
            return packet.getBody();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }


    @Override
    public String serializeMessage(Message message) {
        try {
            ObjectMapper mapper = createObjectMapper();
            ProtocolPacket packet = new ProtocolPacket(message.getMessageType(), message);
            return mapper.writeValueAsString(packet);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T extends Message> T convertObject(Message message, Class<T> targetType) {
        ObjectMapper mapper = createObjectMapper();
        return mapper.convertValue(message, targetType);
    }
}
