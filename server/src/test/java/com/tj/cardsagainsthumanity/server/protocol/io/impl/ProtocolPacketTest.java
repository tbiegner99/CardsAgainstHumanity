package com.tj.cardsagainsthumanity.server.protocol.io.impl;

import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.JoinGameCommand;
import com.tj.cardsagainsthumanity.server.protocol.message.Message;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProtocolPacketTest {
    final String message = "packet body";
    ProtocolPacket packet;

    @Before
    public void setUp() throws Exception {
        packet = new ProtocolPacket(Message.Type.COMMAND, new JoinGameCommand());
    }

    @Test
    public void getMessageType() {
        assertEquals(packet.getMessageType(), Message.Type.COMMAND);
    }

    @Test
    public void getBodyText() {
        assertEquals(packet.getBody(), message);
    }
}