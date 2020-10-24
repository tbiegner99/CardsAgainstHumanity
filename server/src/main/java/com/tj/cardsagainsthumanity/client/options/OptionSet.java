package com.tj.cardsagainsthumanity.client.options;

public interface OptionSet {
    Option[] getOptions(OptionContext context);

    String getPrompt(OptionContext context);

    default Option selectOption(OptionContext context, int index) throws IndexOutOfBoundsException {
        return getOptions(context)[index];
    }
}
