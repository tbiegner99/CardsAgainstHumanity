package com.tj.cardsagainsthumanity.client.options.processor;

import com.tj.cardsagainsthumanity.client.io.InputReader;
import com.tj.cardsagainsthumanity.client.io.OutputWriter;
import com.tj.cardsagainsthumanity.client.io.connection.ServerConnection;
import com.tj.cardsagainsthumanity.client.options.OptionContext;
import com.tj.cardsagainsthumanity.client.options.OptionProcessor;
import com.tj.cardsagainsthumanity.client.options.processor.result.ProcessorResult;
import com.tj.cardsagainsthumanity.client.options.types.gameManagement.CreateGameOption;
import com.tj.cardsagainsthumanity.models.gameStatus.GameStatus;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.CreateGameCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.CreateGameRequest;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.GameResponse;
import com.tj.cardsagainsthumanity.server.protocol.message.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateOptionProcessor implements OptionProcessor<CreateGameOption> {
    private final ServerConnection connection;
    private InputReader inputReader;
    private OutputWriter outputWriter;


    @Autowired
    public CreateOptionProcessor(InputReader reader, OutputWriter writer, ServerConnection connection) {
        this.inputReader = reader;
        this.outputWriter = writer;
        this.connection = connection;
    }

    @Override
    public ProcessorResult processOption(CreateGameOption option, OptionContext context) {
        Command cmd = sendCommand(context);
        GameResponse response = connection.waitForResponse(GameResponse.class, cmd.getMessageId());
        notifyCreationStatus(response);

        GameStatus body = response.getBody();

        return ProcessorResult.success(body);

    }

    private Command sendCommand(OptionContext context) {
        Integer playerId = context.getPlayer().get().getId();
        CreateGameRequest request = new CreateGameRequest();
        request.setPlayerId(playerId);
        CreateGameCommand command = new CreateGameCommand(request);
        connection.sendCommand(command);
        return command;
    }

    private void notifyCreationStatus(GameResponse createResponse) {
        if (!createResponse.isErrorResponse()) {
            outputWriter.writeLine("Created new game with entry code: " + createResponse.getBody().getCode());
        } else {
            outputWriter.writeLine("Error creating Game: " + createResponse.getStatusMessage());
        }
    }


}
