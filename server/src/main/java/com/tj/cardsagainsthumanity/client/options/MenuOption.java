package com.tj.cardsagainsthumanity.client.options;

public interface MenuOption extends Option {
    OptionSet getSubOptions(OptionContext context);
}
