package com.tj.cardsagainsthumanity.server.protocol.impl.message.command;

import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.JoinGameRequest;
import com.tj.cardsagainsthumanity.server.protocol.io.impl.JSONSerializer;
import com.tj.cardsagainsthumanity.server.protocol.message.Message;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

@Ignore
public class JoinGameCommandTest {

    @Test
    public void testJsonSerialization() {
        JSONSerializer serializer = new JSONSerializer();
        JoinGameRequest joinGameRequest = new JoinGameRequest();
        joinGameRequest.setCode("someCode");
        joinGameRequest.setPlayerId(6);
        JoinGameCommand command = new JoinGameCommand(joinGameRequest);
        command.setMessageId("someId");
        String expectedData = "{\"messageType\":\"COMMAND\",\"bodyText\":\"{\\\"messageId\\\":\\\"someId\\\",\\\"commandName\\\":\\\"JOIN\\\",\\\"arguments\\\":{\\\"playerId\\\":6,\\\"code\\\":\\\"someCode\\\"}}\"}";
        String serializedData = serializer.serializeMessage(command);
        assertEquals(serializedData, expectedData);
    }

    @Test
    public void testCommand() {
        JoinGameCommand cmd = new JoinGameCommand();
        assertNull(cmd.getArguments());
        assertEquals(cmd.getMessageType(), Message.Type.COMMAND);
        assertEquals(cmd.getCommandName(), "JOIN");
    }


    @Test
    public void testJsonDeserialization() {
        JSONSerializer serializer = new JSONSerializer();
        String serializedData = "{\"messageType\":\"COMMAND\",\"bodyText\":\"{\\\"arguments\\\":{\\\"playerId\\\":6,\\\"code\\\":\\\"someCode\\\"},\\\"commandName\\\":\\\"JOIN\\\"}\"}";
        BaseCommand command = (BaseCommand) serializer.deserializeMessage(serializedData);
        assertTrue(command instanceof JoinGameCommand);
        assertEquals(command.getCommandName(), "JOIN");
        assertEquals(((JoinGameCommand) command).getArguments().getCode(), "someCode");
        assertEquals(((JoinGameCommand) command).getArguments().getPlayerId().intValue(), 6);
    }
}