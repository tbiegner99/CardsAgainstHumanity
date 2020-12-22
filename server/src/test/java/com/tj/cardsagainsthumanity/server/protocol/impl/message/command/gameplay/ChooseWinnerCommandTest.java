package com.tj.cardsagainsthumanity.server.protocol.impl.message.command.gameplay;

import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundCardPlay;
import com.tj.cardsagainsthumanity.server.protocol.io.impl.JSONSerializer;
import com.tj.cardsagainsthumanity.server.protocol.message.Message;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@Ignore
public class ChooseWinnerCommandTest {
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testJsonSerialization() {
        JSONSerializer serializer = new JSONSerializer();
        RoundCardPlay roundCardPlay = new RoundCardPlay();
        ChooseWinnerCommand roundStatusCommand = new ChooseWinnerCommand(roundCardPlay);
        roundStatusCommand.setMessageId("someId");
        String expectedData = "{\"messageType\":\"COMMAND\",\"bodyText\":\"{\\\"messageId\\\":\\\"someId\\\",\\\"commandName\\\":\\\"CHOOSE_WINNER\\\",\\\"arguments\\\":{\\\"id\\\":null,\\\"cards\\\":null}}\"}";
        String serializedData = serializer.serializeMessage(roundStatusCommand);
        assertEquals(serializedData, expectedData);
    }

    @Test
    public void testCommand() {
        ChooseWinnerCommand cmd = new ChooseWinnerCommand();
        assertNull(cmd.getArguments());
        assertEquals(cmd.getMessageType(), Message.Type.COMMAND);
        assertEquals(cmd.getCommandName(), "CHOOSE_WINNER");
    }


    @Test
    public void testJsonDeserialization() {
        JSONSerializer serializer = new JSONSerializer();
        String serializedData = "{\"messageType\":\"COMMAND\",\"bodyText\":\"{\\\"messageId\\\":\\\"someId\\\",\\\"commandName\\\":\\\"CHOOSE_WINNER\\\",\\\"arguments\\\":{\\\"id\\\":null,\\\"cards\\\":null}}\"}";
        BaseCommand command = (BaseCommand) serializer.deserializeMessage(serializedData);
        assertTrue(command instanceof ChooseWinnerCommand);
        assertEquals(command.getCommandName(), "CHOOSE_WINNER");
    }


}