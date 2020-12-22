package com.tj.cardsagainsthumanity.server.protocol.io;

import com.tj.cardsagainsthumanity.server.protocol.message.Message;

public interface MessageSerializer {
    <T extends Message> T deserializeMessage(String message, Class<T> expectedType);

    Message deserializeMessage(String rawData);

    String serializeMessage(Message message);

    <T extends Message> T convertObject(Message message, Class<T> targetType);
}
