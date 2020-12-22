package com.tj.cardsagainsthumanity.client.options.sets;

import com.tj.cardsagainsthumanity.client.options.Option;
import com.tj.cardsagainsthumanity.client.options.OptionContext;
import com.tj.cardsagainsthumanity.client.options.OptionSet;

public abstract class BaseOptionSet implements OptionSet {

    private final Option[] options;

    public BaseOptionSet(Option... options) {
        this.options = options;
    }


    @Override
    public String getPrompt(OptionContext context) {
        return "";
    }


    @Override
    public Option[] getOptions(OptionContext context) {
        return options;
    }

    public boolean equals(OptionSet options, OptionContext context) {
        return getOptions(context).equals(options.getOptions(context));
    }
}
