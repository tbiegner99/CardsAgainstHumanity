package com.tj.cardsagainsthumanity.client.options.types;

import com.tj.cardsagainsthumanity.client.options.Option;
import org.springframework.stereotype.Component;

@Component
public class LoginOption implements Option {
    @Override
    public String getText() {
        return "Login";
    }
}
