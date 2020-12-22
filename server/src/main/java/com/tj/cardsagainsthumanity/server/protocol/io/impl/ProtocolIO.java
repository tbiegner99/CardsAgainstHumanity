package com.tj.cardsagainsthumanity.server.protocol.io.impl;

import com.tj.cardsagainsthumanity.server.protocol.io.MessageSerializer;
import com.tj.cardsagainsthumanity.server.protocol.io.ProtocolReader;
import com.tj.cardsagainsthumanity.server.protocol.io.ProtocolWriter;
import com.tj.cardsagainsthumanity.server.protocol.message.Command;
import com.tj.cardsagainsthumanity.server.protocol.message.Message;
import com.tj.cardsagainsthumanity.server.protocol.message.Response;

import java.io.*;
import java.util.Objects;

public class ProtocolIO implements ProtocolReader, ProtocolWriter {
    private final BufferedReader scanner;
    private final OutputStream rawOutputStream;
    private InputStream inputStream;
    private PrintWriter outputStream;
    private MessageSerializer serializer;

    public ProtocolIO(InputStream inputStream, OutputStream outputStream, MessageSerializer serializer) {
        this.inputStream = inputStream;
        this.rawOutputStream = outputStream;
        this.outputStream = new PrintWriter(rawOutputStream);
        this.scanner = new BufferedReader(new InputStreamReader(inputStream));
        this.serializer = serializer;
    }

    @Override
    public Message readMessage() throws IOException {
        checkNotInterrupted();
        String message = scanner.readLine();
        System.out.println("RECEIVED: " + message);
        return serializer.deserializeMessage(message);
    }

    @Override
    public <T extends Message> T readMessage(Class<T> expectedType) throws IOException {
        checkNotInterrupted();
        String message = scanner.readLine();
        System.out.println("RECEIVED: " + message);
        return serializer.deserializeMessage(message, expectedType);
    }

    private void checkNotInterrupted() throws InterruptedIOException {
        if (checkAndClearInterruptedFlag()) {
            throw new InterruptedIOException();
        }
    }

    boolean checkAndClearInterruptedFlag() {
        return Thread.interrupted();
    }

    @Override
    public void sendCommand(Command message) {
        sendMessage(message);
    }

    @Override
    public void sendResponse(Response response) {
        sendMessage(response);
    }

    private void sendMessage(Message message) {
        String cmd = serializer.serializeMessage(message);
        outputStream.println(cmd);
        outputStream.flush();
        System.out.println("SENDING: " + cmd);
    }

    @Override
    public <T extends Message> T convertMessage(Message message, Class<T> expectedType) {
        return serializer.convertObject(message, expectedType);
    }

    @Override
    public boolean equals(Object o) {
        ProtocolIO that = (ProtocolIO) o;
        return Objects.equals(inputStream, that.inputStream) &&
                Objects.equals(rawOutputStream, that.rawOutputStream) &&
                Objects.equals(serializer, that.serializer);
    }
}
