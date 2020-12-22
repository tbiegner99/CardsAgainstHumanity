package com.tj.cardsagainsthumanity.client.options.types.gameplay;

import com.tj.cardsagainsthumanity.client.options.Option;
import org.springframework.stereotype.Component;

@Component
public class ViewScoresOption implements Option {
    @Override
    public String getText() {
        return "View Scoreboard";
    }
}