package com.tj.cardsagainsthumanity.client.options.processor.gameManagement;

import com.tj.cardsagainsthumanity.client.io.connection.ServerConnection;
import com.tj.cardsagainsthumanity.client.model.GameState;
import com.tj.cardsagainsthumanity.client.options.OptionContext;
import com.tj.cardsagainsthumanity.client.options.OptionProcessor;
import com.tj.cardsagainsthumanity.client.options.processor.result.ProcessorResult;
import com.tj.cardsagainsthumanity.client.options.types.gameManagement.StartGameOption;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.StartGameCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.GameRequest;
import com.tj.cardsagainsthumanity.server.protocol.message.Response;
import org.springframework.stereotype.Component;

@Component
public class StartGameOptionProcessor implements OptionProcessor<StartGameOption> {
    @Override
    public ProcessorResult processOption(StartGameOption option, OptionContext context) {
        ServerConnection connection = context.getConnection();
        Integer gameId = context.getGameState().getCurrentGameId().get();
        GameRequest request = new GameRequest(gameId);
        StartGameCommand command = new StartGameCommand(request);
        connection.sendCommand(command);
        Response response = connection.waitForResponse(Response.class, command.getMessageId());
        if (response.isErrorResponse()) {
            return ProcessorResult.failure();
        }
        return ProcessorResult.success(
                GameState.builder(context.getGameState())
                        .build()
        );
    }
}
