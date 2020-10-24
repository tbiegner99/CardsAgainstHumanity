package com.tj.cardsagainsthumanity.server.protocol.impl.message.command;

import com.tj.cardsagainsthumanity.server.protocol.impl.ProtocolCommandName;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.LoginInfo;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundStatus;
import com.tj.cardsagainsthumanity.server.protocol.io.impl.JSONSerializer;
import com.tj.cardsagainsthumanity.server.protocol.message.Message;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

public class RoundStatusCommandTest {
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testJsonSerialization() {
        JSONSerializer serializer = new JSONSerializer();
        RoundStatus roundStatus = new RoundStatus();
        RoundStatusCommand roundStatusCommand = new RoundStatusCommand(roundStatus);
        roundStatusCommand.setMessageId("someId");
        String expectedData = "{\"messageType\":\"COMMAND\",\"bodyText\":\"{\\\"messageId\\\":\\\"someId\\\",\\\"commandName\\\":\\\"ROUND_STATUS\\\",\\\"arguments\\\":{\\\"id\\\":null,\\\"blackCard\\\":null,\\\"czar\\\":null,\\\"czarIsYou\\\":false,\\\"over\\\":false,\\\"winner\\\":null,\\\"allCardsIn\\\":false,\\\"cardsInPlay\\\":null}}\"}";
        String serializedData = serializer.serializeMessage(roundStatusCommand);
        assertEquals(serializedData, expectedData);
    }

    @Test
    public void testCommand() {
        RoundStatusCommand cmd = new RoundStatusCommand();
        assertNull(cmd.getArguments());
        assertEquals(cmd.getMessageType(), Message.Type.COMMAND);
        assertEquals(cmd.getCommandName(), "ROUND_STATUS");
    }


    @Test
    public void testJsonDeserialization() {
        JSONSerializer serializer = new JSONSerializer();
        String serializedData = "{\"messageType\":\"COMMAND\",\"bodyText\":\"{\\\"messageId\\\":\\\"someId\\\",\\\"commandName\\\":\\\"ROUND_STATUS\\\",\\\"arguments\\\":{\\\"id\\\":null,\\\"blackCard\\\":null,\\\"czar\\\":null,\\\"czarIsYou\\\":false,\\\"over\\\":false,\\\"winner\\\":null,\\\"allCardsIn\\\":false,\\\"cardsInPlay\\\":null}}\"}";
        BaseCommand command = (BaseCommand) serializer.deserializeMessage(serializedData);
        assertTrue(command instanceof RoundStatusCommand);
        assertEquals(command.getCommandName(), "ROUND_STATUS");
    }
}