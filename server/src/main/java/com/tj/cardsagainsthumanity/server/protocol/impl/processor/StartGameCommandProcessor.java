package com.tj.cardsagainsthumanity.server.protocol.impl.processor;

import com.tj.cardsagainsthumanity.server.protocol.CommandProcessor;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.StartGameCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.GameRequest;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.EmptyResponse;
import com.tj.cardsagainsthumanity.server.protocol.message.CommandContext;
import com.tj.cardsagainsthumanity.services.gameplay.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartGameCommandProcessor implements CommandProcessor<StartGameCommand, EmptyResponse> {
    private GameService gameService;

    @Autowired
    public StartGameCommandProcessor(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public EmptyResponse processMessage(StartGameCommand messageToProcess, CommandContext context) {
        GameRequest gameRequest = messageToProcess.getArguments();
        gameService.startGame(gameRequest.getGameId());
        return EmptyResponse.NO_CONTENT.forMessage(messageToProcess.getMessageId());
    }
}
