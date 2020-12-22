package com.tj.cardsagainsthumanity.server.protocol.impl.message.command;

import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.GameRequest;
import com.tj.cardsagainsthumanity.server.protocol.io.impl.JSONSerializer;
import com.tj.cardsagainsthumanity.server.protocol.message.Message;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

@Ignore
public class StartGameCommandTest {

    @Test
    public void testJsonSerialization() {
        JSONSerializer serializer = new JSONSerializer();
        GameRequest request = new GameRequest(6);
        StartGameCommand command = new StartGameCommand(request);
        command.setMessageId("someId");
        String expectedData = "{\"messageType\":\"COMMAND\",\"bodyText\":\"{\\\"messageId\\\":\\\"someId\\\",\\\"commandName\\\":\\\"START_GAME\\\",\\\"arguments\\\":{\\\"gameId\\\":6}}\"}";
        String serializedData = serializer.serializeMessage(command);
        assertEquals(serializedData, expectedData);
    }

    @Test
    public void testCommand() {
        StartGameCommand cmd = new StartGameCommand();
        assertNull(cmd.getArguments());
        assertEquals(cmd.getMessageType(), Message.Type.COMMAND);
        assertEquals(cmd.getCommandName(), "START_GAME");
    }


    @Test
    public void testJsonDeserialization() {
        JSONSerializer serializer = new JSONSerializer();
        String serializedData = "{\"messageType\":\"COMMAND\",\"bodyText\":\"{\\\"arguments\\\":{\\\"gameId\\\":6},\\\"commandName\\\":\\\"START_GAME\\\"}\"}";
        BaseCommand command = (BaseCommand) serializer.deserializeMessage(serializedData);
        assertTrue(command instanceof StartGameCommand);
        assertEquals(command.getCommandName(), "START_GAME");
        assertEquals(((StartGameCommand) command).getArguments().getGameId().intValue(), 6);
    }

}