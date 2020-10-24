package com.tj.cardsagainsthumanity.server.protocol.io.impl;

import com.tj.cardsagainsthumanity.server.protocol.io.MessageSerializer;
import com.tj.cardsagainsthumanity.server.protocol.message.Command;
import com.tj.cardsagainsthumanity.server.protocol.message.Message;
import com.tj.cardsagainsthumanity.server.protocol.message.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.*;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProtocolIOTest {
    final String testMessage = "TEST_MESSAGE";
    final String serializedString = "TEST_MESSAGE";
    ProtocolIO io;
    ByteArrayOutputStream outputStream;
    InputStream inputStream;
    @Mock
    MessageSerializer serializer;
    @Mock
    Message parsedMessage;
    @Mock
    Command command;
    @Mock
    Response response;
    @Mock
    Message messageToConvert;
    @Mock
    Message convertedMessage;


    @Before
    public void setUp() throws Exception {
        outputStream = spy(new ByteArrayOutputStream());
        inputStream = new ByteArrayInputStream(testMessage.getBytes());
        io = new ProtocolIO(inputStream, outputStream, serializer);
        when(serializer.deserializeMessage(testMessage)).thenReturn(parsedMessage);
        when(serializer.deserializeMessage(eq(testMessage), any())).thenReturn(parsedMessage);
        when(serializer.serializeMessage(any())).thenReturn(serializedString);
        when(serializer.convertObject(messageToConvert,Message.class)).thenReturn(convertedMessage);
    }

    @Test
    public void readMessage() throws Exception {
        Message mess = io.readMessage();
        verify(serializer, times(1)).deserializeMessage(testMessage);
        assertSame(mess, parsedMessage);
    }

    @Test
    public void readMessage_whenThreadInterrupted_throwsInterruptedIoException() throws Exception {
        Thread.currentThread().interrupt();
        try {
            io.readMessage();
            fail("expected exception to be thrown");
        } catch (InterruptedIOException ex) {
            return;
        }
    }


    @Test
    public void readMessage1() throws Exception {
        Message mess = io.readMessage(Command.class);
        verify(serializer, times(1)).deserializeMessage(testMessage, Command.class);
        assertSame(mess, parsedMessage);
    }

    @Test
    public void convertMessage() throws Exception {
        Message mess = io.convertMessage(messageToConvert, Message.class);
        verify(serializer, times(1)).convertObject(messageToConvert, Message.class);
        assertSame(mess, convertedMessage);
    }

    @Test
    public void sendCommand() throws IOException {
        io.sendCommand(command);
        verify(serializer, times(1)).serializeMessage(command);
        verify(outputStream, times(1)).flush();
        assertArrayEquals(outputStream.toByteArray(), ( serializedString + "\n" ).getBytes());
    }

    @Test
    public void sendResponse() throws IOException {
        io.sendResponse(response);
        verify(serializer, times(1)).serializeMessage(response);
        verify(outputStream, times(1)).flush();
        assertArrayEquals(outputStream.toByteArray(), ( serializedString + "\n" ).getBytes());
    }
}