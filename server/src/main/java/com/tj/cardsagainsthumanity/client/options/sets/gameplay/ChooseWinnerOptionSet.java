package com.tj.cardsagainsthumanity.client.options.sets.gameplay;

import com.tj.cardsagainsthumanity.client.options.Option;
import com.tj.cardsagainsthumanity.client.options.OptionContext;
import com.tj.cardsagainsthumanity.client.options.OptionSet;
import com.tj.cardsagainsthumanity.client.options.types.gameplay.ChooseWinnerOption;
import com.tj.cardsagainsthumanity.models.gameStatus.RoundStatus;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundBlackCard;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundCardPlay;

import java.util.Set;

public class ChooseWinnerOptionSet implements OptionSet {
    public ChooseWinnerOptionSet(OptionContext context) {
    }

    @Override
    public Option[] getOptions(OptionContext context) {
        RoundStatus currentRound = context.getGameState().getRound();
        RoundBlackCard blackCard = currentRound.getBlackCard();
        Set<RoundCardPlay> cardsInPlay = currentRound.getRevealedPlays();
        return cardsInPlay.stream().map(play -> new ChooseWinnerOption(blackCard, play)).toArray(Option[]::new);
    }

    @Override
    public String getPrompt(OptionContext context) {
        return "Choose winning play: ";
    }
}
