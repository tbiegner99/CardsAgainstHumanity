package com.tj.cardsagainsthumanity.server.protocol.impl.message.command;

import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.LoginInfo;
import com.tj.cardsagainsthumanity.server.protocol.io.impl.JSONSerializer;
import com.tj.cardsagainsthumanity.server.protocol.message.Message;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@Ignore
public class LoginCommandTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testJsonSerializationOfLoginCommand() {
        JSONSerializer serializer = new JSONSerializer();
        LoginInfo loginInfo = new LoginInfo("test@example.com", "test1234");
        LoginCommand loginCommand = new LoginCommand(loginInfo);
        loginCommand.setMessageId("someId");
        String expectedData = "{\"messageType\":\"COMMAND\",\"bodyText\":\"{\\\"messageId\\\":\\\"someId\\\",\\\"commandName\\\":\\\"LOGIN\\\",\\\"arguments\\\":{\\\"username\\\":\\\"test@example.com\\\",\\\"password\\\":\\\"test1234\\\"}}\"}";
        String serializedData = serializer.serializeMessage(loginCommand);
        assertEquals(serializedData, expectedData);
    }

    @Test
    public void testLoginCommand() {
        LoginCommand cmd = new LoginCommand();
        assertNull(cmd.getArguments());
        assertEquals(cmd.getMessageType(), Message.Type.COMMAND);
        assertEquals(cmd.getCommandName(), "LOGIN");
    }


    @Test
    public void testJsonDeserializationOfLoginCommand() {
        JSONSerializer serializer = new JSONSerializer();
        String serializedData = "{\"messageType\":\"COMMAND\",\"bodyText\":\"{\\\"arguments\\\":{\\\"username\\\":\\\"test@example.com\\\",\\\"password\\\":\\\"test1234\\\"},\\\"commandName\\\":\\\"LOGIN\\\",\\\"messageId\\\":\\\"someId\\\"}\"}";
        BaseCommand command = (BaseCommand) serializer.deserializeMessage(serializedData);
        assertTrue(command instanceof LoginCommand);
        assertEquals(command.getCommandName(), "LOGIN");
    }

    @Test
    public void testToString() {
        LoginInfo loginInfo = new LoginInfo("test@example.com", "test1234");
        LoginCommand loginCommand = new LoginCommand(loginInfo);
        System.out.println(loginCommand.toString());
    }
}