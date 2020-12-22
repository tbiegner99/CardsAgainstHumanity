package com.tj.cardsagainsthumanity.client.options.processor.gameplay;

import com.tj.cardsagainsthumanity.client.io.OutputWriter;
import com.tj.cardsagainsthumanity.client.options.OptionContext;
import com.tj.cardsagainsthumanity.client.options.OptionProcessor;
import com.tj.cardsagainsthumanity.client.options.processor.result.ProcessorResult;
import com.tj.cardsagainsthumanity.client.options.types.gameplay.ShowHandOption;
import com.tj.cardsagainsthumanity.models.gameStatus.PlayerGameStatus;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundWhiteCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class ShowHandProcessor implements OptionProcessor<ShowHandOption> {
    private final OutputWriter output;

    @Autowired
    public ShowHandProcessor(OutputWriter writer) {
        this.output = writer;
    }

    @Override
    public ProcessorResult processOption(ShowHandOption option, OptionContext context) {
        printCurrentHand(context);
        return ProcessorResult.success();
    }

    private void printCurrentHand(OptionContext context) {
        int index = 1;
        Collection<RoundWhiteCard> cards = ((PlayerGameStatus) context.getGameState()).getHandCards();
        for (RoundWhiteCard card : cards) {
            String cardString = String.format("%d) %s", index++, card.getText());
            output.writeLine(cardString);
        }

    }
}
