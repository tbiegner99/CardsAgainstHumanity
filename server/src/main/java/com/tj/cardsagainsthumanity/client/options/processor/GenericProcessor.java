package com.tj.cardsagainsthumanity.client.options.processor;

import com.tj.cardsagainsthumanity.client.options.*;
import com.tj.cardsagainsthumanity.client.options.processor.gameManagement.StartGameOptionProcessor;
import com.tj.cardsagainsthumanity.client.options.processor.gameplay.ChooseWinnerProcessor;
import com.tj.cardsagainsthumanity.client.options.processor.gameplay.PlayCardProcessor;
import com.tj.cardsagainsthumanity.client.options.processor.gameplay.ShowHandProcessor;
import com.tj.cardsagainsthumanity.client.options.processor.result.ProcessorResult;
import com.tj.cardsagainsthumanity.client.options.types.LoginOption;
import com.tj.cardsagainsthumanity.client.options.types.common.BackOption;
import com.tj.cardsagainsthumanity.client.options.types.gameManagement.CreateGameOption;
import com.tj.cardsagainsthumanity.client.options.types.gameManagement.JoinGameOption;
import com.tj.cardsagainsthumanity.client.options.types.gameManagement.StartGameOption;
import com.tj.cardsagainsthumanity.client.options.types.gameplay.ChooseWinnerOption;
import com.tj.cardsagainsthumanity.client.options.types.gameplay.PlayCardOption;
import com.tj.cardsagainsthumanity.client.options.types.gameplay.ShowHandOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenericProcessor implements OptionProcessor<Option> {
    private final CreateOptionProcessor createGameProcessor;
    private final JoinOptionProcessor joinGameProcessor;
    private final StartGameOptionProcessor startGameProcessor;
    private final ShowHandProcessor showHandProcessor;
    private final PlayCardProcessor cardPlayProcessor;
    private final ChooseWinnerProcessor chooseWinnerProcessor;
    private OptionManager manager;
    private LoginProcessor loginProcessor;

    @Autowired
    public GenericProcessor(OptionManager manager, ShowHandProcessor showHandProcessor, PlayCardProcessor cardPlayProcessor, LoginProcessor loginProcessor, StartGameOptionProcessor startGameProcessor, CreateOptionProcessor createGameProcessor, JoinOptionProcessor joinGameProcessor, ChooseWinnerProcessor chooseWinnerProcessor
    ) {
        this.manager = manager;
        this.showHandProcessor = showHandProcessor;
        this.cardPlayProcessor = cardPlayProcessor;
        this.chooseWinnerProcessor = chooseWinnerProcessor;
        this.loginProcessor = loginProcessor;
        this.createGameProcessor = createGameProcessor;
        this.joinGameProcessor = joinGameProcessor;
        this.startGameProcessor = startGameProcessor;
    }

    @Override
    public ProcessorResult processOption(Option option, OptionContext context) {
        if (option instanceof MenuOption) {
            return handleMenuOption((MenuOption) option, context);
        } else if (option instanceof BackOption) {
            manager.back();
            return ProcessorResult.success(context.getGameState());
        } else if (option instanceof LoginOption) {
            return loginProcessor.processOption((LoginOption) option, context);
        } else if (option instanceof CreateGameOption) {
            return createGameProcessor.processOption((CreateGameOption) option, context);
        } else if (option instanceof JoinGameOption) {
            return joinGameProcessor.processOption((JoinGameOption) option, context);
        } else if (option instanceof StartGameOption) {
            return startGameProcessor.processOption((StartGameOption) option, context);
        } else if (option instanceof ShowHandOption) {
            return showHandProcessor.processOption((ShowHandOption) option, context);
        } else if (option instanceof PlayCardOption) {
            return cardPlayProcessor.processOption((PlayCardOption) option, context);
        } else if (option instanceof ChooseWinnerOption) {
            return chooseWinnerProcessor.processOption((ChooseWinnerOption) option, context);
        } else {
            throw new RuntimeException("Unexpected option type");
        }

    }

    private ProcessorResult handleMenuOption(MenuOption menuOption, OptionContext context) {
        manager.pushOptions(menuOption.getSubOptions(context));
        return ProcessorResult.noOp();
    }

}