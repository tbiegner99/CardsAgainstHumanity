package com.tj.cardsagainsthumanity.client.options.sets.gameplay;

import com.tj.cardsagainsthumanity.client.io.OutputWriter;
import com.tj.cardsagainsthumanity.client.options.OptionContext;
import com.tj.cardsagainsthumanity.client.options.sets.BaseOptionSet;
import com.tj.cardsagainsthumanity.client.options.types.gameplay.PlayCardOption;
import com.tj.cardsagainsthumanity.client.options.types.gameplay.ShowHandOption;
import com.tj.cardsagainsthumanity.client.options.types.gameplay.ViewScoresOption;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundBlackCard;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlayerOptionSet extends BaseOptionSet {
    @Autowired
    public PlayerOptionSet(ViewScoresOption viewScores, PlayCardOption play, ShowHandOption showHandOption) {
        super(viewScores, showHandOption, play);
    }

    @Override
    public String getPrompt(OptionContext context) {
        return roundStateToString(context.getGameState().getCurrentRound().get());
    }


    private String roundStateToString(RoundStatus currentRound) {
        return String.format("\n\n%s\n%s\n\n", printCzar(currentRound), printBlackCard(currentRound));
    }

    private String printBlackCard(RoundStatus currentRound) {
        RoundBlackCard card = currentRound.getBlackCard();
        String textColor = OutputWriter.Colors.Foreground.BLACK;
        String bgColor = OutputWriter.Colors.Background.WHITE;
        return String.format("Black card:\n%s%s%s%s", bgColor, textColor, card.getText(), OutputWriter.Colors.RESET);
    }

    private String printCzar(RoundStatus currentRound) {
        if (currentRound.isCzarIsYou()) {
            return "You are the czar";
        }
        return "Czar: " + currentRound.getCzar();
    }
}
