package com.tj.cardsagainsthumanity.server.protocol.impl.message.command;

import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseCommand;
import com.tj.cardsagainsthumanity.server.protocol.io.impl.JSONSerializer;
import com.tj.cardsagainsthumanity.server.protocol.message.Message;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameStatusCommandTest {
    @Test
    public void testJsonSerialization() {
        JSONSerializer serializer = new JSONSerializer();
        GameStatusCommand command = new GameStatusCommand();
        command.setMessageId("someId");
        String expectedData = "{\"messageType\":\"COMMAND\",\"bodyText\":\"{\\\"messageId\\\":\\\"someId\\\",\\\"commandName\\\":\\\"GAME_STATUS\\\",\\\"arguments\\\":null}\"}";
        String serializedData = serializer.serializeMessage(command);
        assertEquals(serializedData, expectedData);
    }

    @Test
    public void testCommand() {
        GameStatusCommand cmd = new GameStatusCommand();
        assertNull(cmd.getArguments());
        assertEquals(cmd.getMessageType(), Message.Type.COMMAND);
        assertEquals(cmd.getCommandName(), "GAME_STATUS");
    }


    @Test
    public void testJsonDeserialization() {
        JSONSerializer serializer = new JSONSerializer();
        String serializedData = "{\"messageType\":\"COMMAND\",\"bodyText\":\"{\\\"messageId\\\":\\\"someId\\\",\\\"arguments\\\":null,\\\"commandName\\\":\\\"GAME_STATUS\\\"}\"}";
        BaseCommand command = (BaseCommand) serializer.deserializeMessage(serializedData);
        assertTrue(command instanceof GameStatusCommand);
        assertEquals(command.getCommandName(), "GAME_STATUS");
        assertEquals(command.getMessageId(), "someId");
    }
}