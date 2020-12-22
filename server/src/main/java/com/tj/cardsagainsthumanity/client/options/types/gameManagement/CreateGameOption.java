package com.tj.cardsagainsthumanity.client.options.types.gameManagement;

import com.tj.cardsagainsthumanity.client.options.Option;
import org.springframework.stereotype.Component;

@Component
public class CreateGameOption implements Option {
    @Override
    public String getText() {
        return "Create Game";
    }
}
