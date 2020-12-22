package com.tj.cardsagainsthumanity.client.options.types.gameplay;

import com.tj.cardsagainsthumanity.client.options.Option;
import org.springframework.stereotype.Component;

@Component
public class ShowHandOption implements Option {
    @Override
    public String getText() {
        return "Show Hand";
    }
}