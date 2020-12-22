package com.tj.cardsagainsthumanity.server.protocol.impl.processor;

import com.tj.cardsagainsthumanity.server.protocol.CommandProcessor;
import com.tj.cardsagainsthumanity.server.protocol.impl.ProtocolCommandName;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.*;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.gameplay.ChooseWinnerCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.gameplay.EndRoundCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.gameplay.PlayCardCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.gameplay.RevealPlayCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.EmptyResponse;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.ErrorResponse;
import com.tj.cardsagainsthumanity.server.protocol.impl.processor.gameplay.*;
import com.tj.cardsagainsthumanity.server.protocol.message.Command;
import com.tj.cardsagainsthumanity.server.protocol.message.CommandContext;
import com.tj.cardsagainsthumanity.server.protocol.message.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("genericProcessor")
public class GenericCommandProcessor implements CommandProcessor<Command, Response> {
    private final PlayCardCommandProcessor playCardProcessor;
    private final ChooseWinnerProcessor chooseWinnerProcessor;
    private CreateGameCommandProcessor createGameProcessor;
    private JoinGameCommandProcessor joinGameCommandProcessor;
    private LoginCommandProcessor loginCommandProcessor;
    private StartGameCommandProcessor startGameCommandProcessor;
    private LoadGameProcessor loadGameProcessor;
    private RevealPlayProcessor revealPlayProcessor;
    private EndRoundProcessor endRoundProcessor;

    @Autowired
    public GenericCommandProcessor(StartGameCommandProcessor startGameCommandProcessor, LoginCommandProcessor loginCommandProcessor, CreateGameCommandProcessor createGameProcessor, JoinGameCommandProcessor joinGameCommandProcessor, PlayCardCommandProcessor playCardCommandProcessor, ChooseWinnerProcessor chooseWinnerProcessor, LoadGameProcessor loadGameProcessor, RevealPlayProcessor revealPlayProcessor, EndRoundProcessor endRoundProcessor) {
        this.createGameProcessor = createGameProcessor;
        this.joinGameCommandProcessor = joinGameCommandProcessor;
        this.playCardProcessor = playCardCommandProcessor;
        this.loginCommandProcessor = loginCommandProcessor;
        this.startGameCommandProcessor = startGameCommandProcessor;
        this.chooseWinnerProcessor = chooseWinnerProcessor;
        this.loadGameProcessor = loadGameProcessor;
        this.revealPlayProcessor = revealPlayProcessor;
        this.endRoundProcessor = endRoundProcessor;
    }

    //TODO: move to factory?
    //  private CommandProcessor getProcessorForCommandName(ProtocolCommandName commandName) {
    //    return null;
    //}

    @Override
    public Response processMessage(Command messageToProcess, CommandContext context) {
        ProtocolCommandName commandName;
        try {
            commandName = ProtocolCommandName.valueOf(messageToProcess.getCommandName());

        } catch (IllegalArgumentException e) {
            return EmptyResponse.METHOD_NOT_FOUND;
        }
        try {
            if (loginIsRequiredForCommand(messageToProcess, context)) {
                return ErrorResponse.forMessageId(messageToProcess.getMessageId()).unauthorized("Login is required");
            }


            switch (commandName) {
                case LOGIN:
                    return loginCommandProcessor.processMessage((LoginCommand) messageToProcess, context);
                case CREATE_GAME:
                    return createGameProcessor.processMessage((CreateGameCommand) messageToProcess, context);
                case JOIN:
                    return joinGameCommandProcessor.processMessage((JoinGameCommand) messageToProcess, context);
                case START_GAME:
                    return startGameCommandProcessor.processMessage((StartGameCommand) messageToProcess, context);
                case PLAY_CARD:
                    return playCardProcessor.processMessage((PlayCardCommand) messageToProcess, context);
                case CHOOSE_WINNER:
                    return chooseWinnerProcessor.processMessage((ChooseWinnerCommand) messageToProcess, context);
                case LOAD_GAME:
                    return loadGameProcessor.processMessage((LoadGameCommand) messageToProcess, context);
                case REVEAL:
                    return revealPlayProcessor.processMessage((RevealPlayCommand) messageToProcess, context);
                case END_ROUND:
                    return endRoundProcessor.processMessage((EndRoundCommand) messageToProcess, context);
                case GAME_STATUS:
                case LEAVE:
                case LIKE:
                case DISLIKE:
                case ROUND_STATUS:

                default:
                    return EmptyResponse.METHOD_NOT_FOUND;
            }
        } catch (IllegalStateException e) {
            return ErrorResponse.forMessageId(messageToProcess.getMessageId()).forbidden(e.getMessage(), e.getClass().getName());
        } catch (Exception e) {
            e.printStackTrace(); //TODO: log
            return EmptyResponse.INTERNAL_SERVER_ERROR;
        }
    }


    private boolean loginIsRequiredForCommand(Command command, CommandContext context) {
        return !context.getPlayer().isPresent() && command.isLoginRequired();
    }
}
