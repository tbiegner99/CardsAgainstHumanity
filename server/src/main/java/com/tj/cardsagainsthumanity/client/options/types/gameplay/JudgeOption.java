package com.tj.cardsagainsthumanity.client.options.types.gameplay;

import com.tj.cardsagainsthumanity.client.options.MenuOption;
import com.tj.cardsagainsthumanity.client.options.OptionContext;
import com.tj.cardsagainsthumanity.client.options.OptionSet;
import com.tj.cardsagainsthumanity.client.options.sets.gameplay.ChooseWinnerOptionSet;
import org.springframework.stereotype.Component;

@Component
public class JudgeOption implements MenuOption {
    @Override
    public String getText() {
        return "Judge";
    }

    @Override
    public OptionSet getSubOptions(OptionContext context) {
        return new ChooseWinnerOptionSet(context);
    }
}