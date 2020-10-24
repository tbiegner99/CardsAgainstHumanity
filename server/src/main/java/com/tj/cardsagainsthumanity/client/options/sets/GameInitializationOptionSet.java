package com.tj.cardsagainsthumanity.client.options.sets;

import com.tj.cardsagainsthumanity.client.options.types.gameManagement.StartGameOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameInitializationOptionSet extends BaseOptionSet {
    @Autowired
    public GameInitializationOptionSet(StartGameOption startOption) {
        super(startOption);
    }

}
