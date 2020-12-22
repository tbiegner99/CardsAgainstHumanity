package com.tj.cardsagainsthumanity.client.options.processor.gameplay;

import com.tj.cardsagainsthumanity.client.io.InputReader;
import com.tj.cardsagainsthumanity.client.io.OutputWriter;
import com.tj.cardsagainsthumanity.client.io.connection.ServerConnection;
import com.tj.cardsagainsthumanity.client.options.OptionContext;
import com.tj.cardsagainsthumanity.client.options.OptionProcessor;
import com.tj.cardsagainsthumanity.client.options.processor.result.ProcessorResult;
import com.tj.cardsagainsthumanity.client.options.types.gameplay.PlayCardOption;
import com.tj.cardsagainsthumanity.client.utils.CardUtilities;
import com.tj.cardsagainsthumanity.exceptions.PlayCancelledException;
import com.tj.cardsagainsthumanity.models.gameStatus.PlayerGameStatus;
import com.tj.cardsagainsthumanity.models.gameStatus.RoundStatus;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseResponse;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.CardPlayRequest;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundBlackCard;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundWhiteCard;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.gameplay.PlayCardCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PlayCardProcessor implements OptionProcessor<PlayCardOption> {
    private final OutputWriter output;
    private final InputReader reader;
    private final CardUtilities cardUtils;

    @Autowired
    public PlayCardProcessor(OutputWriter writer, InputReader reader, CardUtilities cardUtils) {
        this.output = writer;
        this.reader = reader;
        this.cardUtils = cardUtils;
    }

    @Override
    public ProcessorResult processOption(PlayCardOption option, OptionContext context) {
        RoundStatus roundState = context.getGameState().getRound();
        RoundBlackCard blackCard = roundState.getBlackCard();
        List<RoundWhiteCard> handCards = new ArrayList<>(((PlayerGameStatus) context.getGameState()).getHandCards());
        printBlackCard(blackCard);
        printCurrentHand(handCards);

        List<RoundWhiteCard> choices = getCardsToPlay(handCards, blackCard);
        playCards(context, roundState.getId(), choices);
        return ProcessorResult.success();
    }

    private void playCards(OptionContext context, Integer roundId, List<RoundWhiteCard> choices) {
        ServerConnection connection = context.getConnection();
        CardPlayRequest request = new CardPlayRequest(roundId, choices);
        PlayCardCommand command = new PlayCardCommand(request);
        connection.waitForResponse(BaseResponse.class, command);

    }

    private List<RoundWhiteCard> getCardsToPlay(List<RoundWhiteCard> handCards, RoundBlackCard blackCard) {
        while (true) {
            try {
                List<RoundWhiteCard> choices = chooseCardsFromHand(handCards, blackCard.getNumberOfAnswers());
                confirmChoices(blackCard, choices);
                return choices;
            } catch (PlayCancelledException ex) {
                continue;
            }

        }
    }

    private void confirmChoices(RoundBlackCard blackCard, List<RoundWhiteCard> choices) {
        printPlay(blackCard, choices);
        output.write("Make this play? (y/n): ");
        while (true) {
            String confirm = reader.readLine();
            if (confirm.equals("y")) {
                return;
            } else if (confirm.equals("n")) {
                throw new PlayCancelledException();
            }
            output.writeLine(String.format("Unknown option %s. Enter y or n: ", confirm));
        }
    }

    private void printBlackCard(RoundBlackCard blackCard) {
        output.writeLine("\nCard: ");
        output.writeLine(cardUtils.printBlackCard(blackCard));
        output.writeLine("");
    }

    private void printPlay(RoundBlackCard blackCard, List<RoundWhiteCard> choices) {
        output.writeLine("Your play: ");
        output.writeLine(cardUtils.generatePlayForCard(blackCard, choices));
    }


    private List<RoundWhiteCard> chooseCardsFromHand(List<RoundWhiteCard> handCards, int numberOfAnswers) {
        List<RoundWhiteCard> chosen = new ArrayList<>();
        Set<Integer> choices = new HashSet<>();
        for (int i = 0; i < numberOfAnswers; i++) {
            chosen.add(selectCard(handCards, choices, i));
        }
        return chosen;
    }

    private RoundWhiteCard selectCard(List<RoundWhiteCard> handCards, Set<Integer> choicesMade, int answerIndex) {
        while (true) {
            try {
                output.write(String.format("Select answer %d for card: ", answerIndex + 1));
                Integer choice = reader.readInteger();
                return chooseCard(choicesMade, handCards, choice);
            } catch (IllegalArgumentException e) {
                output.writeLine("Illegal Option provided");
            } catch (InputMismatchException e) {
                reader.readLine();
                output.writeLine("Option must be a number of the card. Please try again.");
            }
        }
    }

    private RoundWhiteCard chooseCard(Set<Integer> choices, List<RoundWhiteCard> handCards, Integer choice) {
        int choiceIndex = choice - 1;
        if (choices.contains(choice)) {
            throw new IllegalArgumentException();
        }
        if (choiceIndex == handCards.size()) {
            throw new PlayCancelledException();
        }
        choices.add(choice);
        return handCards.get(choiceIndex);
    }


    private void printCurrentHand(List<RoundWhiteCard> cards) {
        int index = 1;
        for (RoundWhiteCard card : cards) {
            String cardString = String.format("%d) %s", index++, card.getText());
            output.writeLine(cardString);
        }
        output.writeLine(String.format("%d) Start Over", index));
        output.writeLine("");

    }
}


