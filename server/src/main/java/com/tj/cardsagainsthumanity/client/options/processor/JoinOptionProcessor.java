package com.tj.cardsagainsthumanity.client.options.processor;

import com.tj.cardsagainsthumanity.client.io.InputReader;
import com.tj.cardsagainsthumanity.client.io.OutputWriter;
import com.tj.cardsagainsthumanity.client.io.connection.ServerConnection;
import com.tj.cardsagainsthumanity.client.options.OptionContext;
import com.tj.cardsagainsthumanity.client.options.OptionProcessor;
import com.tj.cardsagainsthumanity.client.options.processor.result.ProcessorResult;
import com.tj.cardsagainsthumanity.client.options.types.gameManagement.JoinGameOption;
import com.tj.cardsagainsthumanity.models.gameStatus.GameStatus;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.JoinGameCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.JoinGameRequest;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.GameResponse;
import com.tj.cardsagainsthumanity.server.protocol.message.Command;
import com.tj.cardsagainsthumanity.server.protocol.message.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JoinOptionProcessor implements OptionProcessor<JoinGameOption> {
    private final ServerConnection connection;
    private InputReader inputReader;
    private OutputWriter outputWriter;


    @Autowired
    public JoinOptionProcessor(InputReader reader, OutputWriter writer, ServerConnection connection) {
        this.inputReader = reader;
        this.outputWriter = writer;
        this.connection = connection;
    }

    @Override
    public ProcessorResult processOption(JoinGameOption option, OptionContext context) {
        GameResponse response = null;

        String code = getCode();
        Command cmd = sendCommand(code, context.getPlayer().get(), context.getGameState());
        response = connection.waitForResponse(GameResponse.class, cmd.getMessageId());
        notifyCreationStatus(response);

        if (response.isErrorResponse()) {
            return ProcessorResult.failure(context.getGameState());
        }

        return ProcessorResult.success(response.getBody());
    }

    private String getCode() {
        outputWriter.write("Enter game code to join: ");
        return inputReader.readLine();
    }

    private Command sendCommand(String code, Player currentPlayer, GameStatus state) {
        JoinGameRequest request = new JoinGameRequest();
        request.setCode(code);
        request.setPlayerId(currentPlayer.getId());
        JoinGameCommand command = new JoinGameCommand(request);
        connection.sendCommand(command);
        return command;
    }

    private void notifyCreationStatus(Response createResponse) {
        if (!createResponse.isErrorResponse()) {
            outputWriter.writeLine("Joined game with id: " + createResponse.getBody());
        } else {
            outputWriter.writeLine("Error joining game: " + createResponse.getStatusMessage());
        }
    }

}
