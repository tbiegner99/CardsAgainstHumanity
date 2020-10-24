package com.tj.cardsagainsthumanity.server.protocol.io;

import com.tj.cardsagainsthumanity.server.protocol.message.Message;

import javax.validation.UnexpectedTypeException;
import java.io.IOException;

public interface ProtocolReader {
    <T extends Message> T convertMessage(Message message, Class<T> expectedType);

    Message readMessage() throws IOException;

    <T extends Message> T readMessage(Class<T> expectedType) throws UnexpectedTypeException, IOException;
}
