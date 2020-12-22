package com.tj.cardsagainsthumanity.server.protocol.io;

import com.tj.cardsagainsthumanity.server.protocol.message.Command;
import com.tj.cardsagainsthumanity.server.protocol.message.Response;

import java.io.IOException;

public interface ProtocolWriter {
    void sendCommand(Command message) throws IOException;

    void sendResponse(Response response) throws IOException;
}
