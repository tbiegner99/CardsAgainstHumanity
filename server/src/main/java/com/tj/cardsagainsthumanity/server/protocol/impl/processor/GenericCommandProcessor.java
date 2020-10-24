package com.tj.cardsagainsthumanity.server.protocol.impl.processor;

import com.tj.cardsagainsthumanity.server.protocol.CommandProcessor;
import com.tj.cardsagainsthumanity.server.protocol.impl.ProtocolCommandName;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.CreateGameCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.JoinGameCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.LoginCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.StartGameCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.gameplay.ChooseWinnerCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.gameplay.PlayCardCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.EmptyResponse;
import com.tj.cardsagainsthumanity.server.protocol.impl.processor.gameplay.ChooseWinnerProcessor;
import com.tj.cardsagainsthumanity.server.protocol.impl.processor.gameplay.PlayCardCommandProcessor;
import com.tj.cardsagainsthumanity.server.protocol.message.Command;
import com.tj.cardsagainsthumanity.server.protocol.message.CommandContext;
import com.tj.cardsagainsthumanity.server.protocol.message.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.tj.cardsagainsthumanity.server.protocol.impl.ProtocolCommandName.LOGIN;

@Component("genericProcessor")
public class GenericCommandProcessor implements CommandProcessor<Command, Response> {
    private final PlayCardCommandProcessor playCardProcessor;
    private final ChooseWinnerProcessor chooseWinnerProcessor;
    private CreateGameCommandProcessor createGameProcessor;
    private JoinGameCommandProcessor joinGameCommandProcessor;
    private LoginCommandProcessor loginCommandProcessor;
    private StartGameCommandProcessor startGameCommandProcessor;

    @Autowired
    public GenericCommandProcessor(StartGameCommandProcessor startGameCommandProcessor, LoginCommandProcessor loginCommandProcessor, CreateGameCommandProcessor createGameProcessor, JoinGameCommandProcessor joinGameCommandProcessor, PlayCardCommandProcessor playCardCommandProcessor, ChooseWinnerProcessor chooseWinnerProcessor) {
        this.createGameProcessor = createGameProcessor;
        this.joinGameCommandProcessor = joinGameCommandProcessor;
        this.playCardProcessor = playCardCommandProcessor;
        this.loginCommandProcessor = loginCommandProcessor;
        this.startGameCommandProcessor = startGameCommandProcessor;
        this.chooseWinnerProcessor = chooseWinnerProcessor;
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
            //should be command.canExecute(context)

            if (loginIsRequiredForCommand(commandName, context)) {
                return EmptyResponse.FORBIDDEN; // TODO:
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
                case PLAY:
                    return playCardProcessor.processMessage((PlayCardCommand) messageToProcess, context);
                case CHOOSE_WINNER:
                    return chooseWinnerProcessor.processMessage((ChooseWinnerCommand) messageToProcess, context);
                case LEAVE:
                case LIKE:
                case DISLIKE:
                case ROUND_STATUS:
                case GAME_STATUS:
                default:
                    return EmptyResponse.METHOD_NOT_FOUND;
            }
        } catch (Exception e) {
            e.printStackTrace(); //TODO: log
            return EmptyResponse.INTERNAL_SERVER_ERROR;
        }
    }


    private boolean loginIsRequiredForCommand(ProtocolCommandName commandName, CommandContext context) {
        return !context.getPlayer().isPresent() && commandName != LOGIN;
    }
}
