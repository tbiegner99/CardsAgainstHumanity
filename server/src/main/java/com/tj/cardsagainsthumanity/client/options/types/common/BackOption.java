package com.tj.cardsagainsthumanity.client.options.types.common;

import com.tj.cardsagainsthumanity.client.options.Option;
import org.springframework.stereotype.Component;

@Component
public class BackOption implements Option {
    @Override
    public String getText() {
        return "Back";
    }
}
