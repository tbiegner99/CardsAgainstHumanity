package com.tj.cardsagainsthumanity.client.options.sets;

import com.tj.cardsagainsthumanity.client.options.Option;
import com.tj.cardsagainsthumanity.client.options.OptionContext;
import com.tj.cardsagainsthumanity.client.options.OptionSet;

public class EmptyOptionSet implements OptionSet {

    private String prompt;

    public EmptyOptionSet(String prompt) {
        this.prompt = prompt;
    }

    @Override
    public Option[] getOptions(OptionContext context) {
        return new Option[0];
    }

    @Override
    public String getPrompt(OptionContext context) {
        return this.prompt;
    }
}
