package com.tj.cardsagainsthumanity.server.protocol.impl.message.command;

import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.CreateGameRequest;
import com.tj.cardsagainsthumanity.server.protocol.io.impl.JSONSerializer;
import com.tj.cardsagainsthumanity.server.protocol.message.Message;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

@Ignore
public class CreateGameCommandTest {


    @Test
    public void testJsonSerialization() {
        JSONSerializer serializer = new JSONSerializer();
        CreateGameRequest request = new CreateGameRequest();
        request.setPlayerId(6);
        CreateGameCommand command = new CreateGameCommand(request);
        command.setMessageId("someId");
        String expectedData = "{\"messageType\":\"COMMAND\",\"bodyText\":\"{\\\"messageId\\\":\\\"someId\\\",\\\"commandName\\\":\\\"CREATE_GAME\\\",\\\"arguments\\\":{\\\"playerId\\\":6}}\"}";
        String serializedData = serializer.serializeMessage(command);
        assertEquals(serializedData, expectedData);
    }

    @Test
    public void testCommand() {
        CreateGameCommand cmd = new CreateGameCommand();
        assertNull(cmd.getArguments());
        assertEquals(cmd.getMessageType(), Message.Type.COMMAND);
        assertEquals(cmd.getCommandName(), "CREATE_GAME");
    }


    @Test
    public void testJsonDeserialization() {
        JSONSerializer serializer = new JSONSerializer();
        String serializedData = "{\"messageType\":\"COMMAND\",\"bodyText\":\"{\\\"arguments\\\":{\\\"playerId\\\":6},\\\"commandName\\\":\\\"CREATE_GAME\\\"}\"}";
        BaseCommand command = (BaseCommand) serializer.deserializeMessage(serializedData);
        assertTrue(command instanceof CreateGameCommand);
        assertEquals(command.getCommandName(), "CREATE_GAME");
        assertEquals(((CreateGameCommand) command).getArguments().getPlayerId().intValue(), 6);
    }

}