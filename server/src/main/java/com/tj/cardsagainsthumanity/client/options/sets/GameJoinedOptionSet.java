package com.tj.cardsagainsthumanity.client.options.sets;

import com.tj.cardsagainsthumanity.client.options.Option;
import com.tj.cardsagainsthumanity.client.options.OptionContext;
import com.tj.cardsagainsthumanity.client.options.types.gameManagement.ChangeGameManagerOption;
import com.tj.cardsagainsthumanity.client.options.types.gameManagement.LeaveGameOption;
import com.tj.cardsagainsthumanity.client.options.types.gameManagement.StartGameOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameJoinedOptionSet extends BaseOptionSet {

    @Autowired
    public GameJoinedOptionSet(StartGameOption startGame, ChangeGameManagerOption changeManager, LeaveGameOption leaveGameOption) {
        super(startGame, changeManager, leaveGameOption);
    }

    @Override
    public String getPrompt(OptionContext context) {
        if (!context.isPlayerGameManager()) {
            return "\n\nWaiting for game to start....\n\n";
        }
        return super.getPrompt(context);
    }

    @Override
    public Option[] getOptions(OptionContext context) {
        if (!context.isPlayerGameManager()) {
            return new Option[0];
        }
        return super.getOptions(context);
    }
}
