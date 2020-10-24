package com.tj.cardsagainsthumanity.client.io.connection;

import com.tj.cardsagainsthumanity.client.client.GameStateChangeHandler;
import com.tj.cardsagainsthumanity.exceptions.RuntimeInterruptException;
import com.tj.cardsagainsthumanity.server.protocol.impl.ProtocolCommandName;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.GameStatusCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.RoundStatusCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.EmptyResponse;
import com.tj.cardsagainsthumanity.server.protocol.io.ProtocolReader;
import com.tj.cardsagainsthumanity.server.protocol.io.ProtocolWriter;
import com.tj.cardsagainsthumanity.server.protocol.message.Command;
import com.tj.cardsagainsthumanity.server.protocol.message.Message;
import com.tj.cardsagainsthumanity.server.protocol.message.Response;

import java.io.IOException;

public class ReaderThread extends Thread {
    private ProtocolReader reader;
    private ProtocolWriter writer;
    private Response lastResponse;
    private GameStateChangeHandler gameStateChangeHandler;


    ReaderThread(ProtocolReader reader, ProtocolWriter writer, GameStateChangeHandler changeHandler) {
        this.reader = reader;
        this.writer = writer;
        this.gameStateChangeHandler = changeHandler;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message message = reader.readMessage();
                if (message.getMessageType() == Message.Type.RESPONSE) {
                    processResponse((Response) message);
                } else {
                    processCommand((Command) message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void processResponse(Response message) {
        lastResponse = message;
        notify();
    }

    public synchronized Response waitForResponseId(String id) {
        try {
            if (lastResponse == null || !lastResponse.getMessageId().equals(id)) {
                wait();
            }
            return lastResponse;
        } catch (InterruptedException e) {
            throw new RuntimeInterruptException(e);
        }
    }

    private void processCommand(Command message) {
        boolean isGameStatusCommand = message.getCommandName().equals(ProtocolCommandName.GAME_STATUS.toString());
        boolean isRoundStatusCommand = message.getCommandName().equals(ProtocolCommandName.ROUND_STATUS.toString());
        if (isGameStatusCommand) {
            GameStatusCommand command = (GameStatusCommand) message;
            gameStateChangeHandler.onGameStateChanged(command.getArguments());
        } else if (isRoundStatusCommand) {
            RoundStatusCommand command = (RoundStatusCommand) message;
            gameStateChangeHandler.onRoundStateChanged(command.getArguments());
        }
        try {
            writer.sendResponse(EmptyResponse.NO_CONTENT);
        }catch(IOException e){}
    }


}
