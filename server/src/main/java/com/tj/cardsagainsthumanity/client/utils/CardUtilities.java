package com.tj.cardsagainsthumanity.client.utils;

import com.tj.cardsagainsthumanity.client.io.OutputWriter;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundBlackCard;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundWhiteCard;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CardUtilities {


    public String printBlackCard(RoundBlackCard blackCard) {
        String text = blackCard.getText();
        String bgColor = OutputWriter.Colors.Background.WHITE;
        String fgColor = OutputWriter.Colors.Foreground.BLACK;
        return String.format("%s%s%s%s", bgColor, fgColor, text, OutputWriter.Colors.RESET);
    }

    public String generatePlayForCard(RoundBlackCard blackCard, List<RoundWhiteCard> choices) {
        String text = replaceText(blackCard, choices);
        String bgColor = OutputWriter.Colors.Background.WHITE;
        String fgColor = OutputWriter.Colors.Foreground.BLACK;
        return String.format("%s%s%s%s", bgColor, fgColor, text, OutputWriter.Colors.RESET);
    }

    private String replaceText(RoundBlackCard blackCard, List<RoundWhiteCard> choices) {
        String text = blackCard.getText();
        int answerLocation = text.indexOf("_");
        String textFg = OutputWriter.Colors.Foreground.BLACK;
        String textBg = OutputWriter.Colors.Background.WHITE;
        String answerFg = OutputWriter.Colors.Foreground.BLACK;
        String answerBg = OutputWriter.Colors.Background.YELLOW;
        String reset = OutputWriter.Colors.RESET;
        if (answerLocation < 0) {
            return String.format("%s%s%s %s%s%s%s", textBg, textFg, text, answerFg, answerBg, choices.get(0).getText(), reset);
        }
        for (int i = 0; answerLocation >= 0; i++) {
            String replacement = String.format("%s%s%s%s%s", answerFg, answerBg, choices.get(i).getText(), textBg, textFg);
            String escapedReplacement = replacement.replaceAll("\\$", "\\$");
            text = text.replaceFirst("_", escapedReplacement);
            answerLocation = text.indexOf("_");
        }
        text += reset;
        return text;
    }
}
