package com.tj.cardsagainsthumanity.client.options.sets.gameplay;

import com.tj.cardsagainsthumanity.client.io.OutputWriter;
import com.tj.cardsagainsthumanity.client.options.OptionContext;
import com.tj.cardsagainsthumanity.client.options.sets.BaseOptionSet;
import com.tj.cardsagainsthumanity.client.options.types.gameplay.JudgeOption;
import com.tj.cardsagainsthumanity.client.options.types.gameplay.ShowHandOption;
import com.tj.cardsagainsthumanity.client.options.types.gameplay.ViewScoresOption;
import com.tj.cardsagainsthumanity.models.gameStatus.RoundStatus;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundBlackCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CzarOptionSet extends BaseOptionSet {
    @Autowired
    public CzarOptionSet(ViewScoresOption viewScores, JudgeOption judge, ShowHandOption showHandOption) {
        super(viewScores, judge, showHandOption);
    }

    @Override
    public String getPrompt(OptionContext context) {
        return roundStateToString(context.getGameState().getRound());
    }


    private String roundStateToString(RoundStatus currentRound) {
        return String.format("\n%s\n%s\n%s\n\n", printCzar(currentRound), printCardsPlayed(currentRound), printBlackCard(currentRound));
    }

    private String printBlackCard(RoundStatus currentRound) {
        RoundBlackCard card = currentRound.getBlackCard();
        String textColor = OutputWriter.Colors.Foreground.BLACK;
        String bgColor = OutputWriter.Colors.Background.WHITE;
        return String.format("Black card:\n%s%s%s%s", bgColor, textColor, card.getText(), OutputWriter.Colors.RESET);
    }

    private String printCardsPlayed(RoundStatus currentRound) {
        if (currentRound.isAllCardsIn()) {
            return "All cards played";
        }
        return String.format("%d cards played", currentRound.getRevealedPlays().size());
    }

    private String printCzar(RoundStatus currentRound) {
        if (currentRound.isCzarIsYou()) {
            return "You are the czar";
        }
        return currentRound.getCzar().getDisplayName();
    }
}
