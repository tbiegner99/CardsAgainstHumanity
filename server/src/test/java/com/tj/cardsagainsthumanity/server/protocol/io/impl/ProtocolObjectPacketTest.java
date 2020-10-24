package com.tj.cardsagainsthumanity.server.protocol.io.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.JoinGameCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.JoinGameRequest;
import com.tj.cardsagainsthumanity.server.protocol.message.Message;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ProtocolObjectPacketTest {
JoinGameCommand command;
    JoinGameRequest req;
    @Before
    public void setUp() throws Exception {
        String code= "some-code";
        Integer player=3;
        req= new JoinGameRequest();
        req.setCode(code);
        req.setPlayerId(player);
        command = new JoinGameCommand(req);
    }

    @Test
    public void jacksonSerializeCommand() throws JsonProcessingException {

        ProtocolObjectPacket packet =new ProtocolObjectPacket(Message.Type.COMMAND,command);
        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(packet);
        System.out.println(result);
    }

    @Test
    public void jacksonDeserialize() throws IOException {
        String json="{\"messageType\":\"COMMAND\",\"body\":{\"messageId\":\"5df5b2a3-c0c4-48b6-b3d6-99f4e8d2d1b1\",\"commandName\":\"JOIN\",\"arguments\":{\"playerId\":3,\"code\":\"some-code\"}}}\n";
        ObjectMapper mapper = new ObjectMapper();
        ProtocolPacket result = mapper.readValue(json,ProtocolPacket.class);
        System.out.println(result.getBody().getClass());
    }
}