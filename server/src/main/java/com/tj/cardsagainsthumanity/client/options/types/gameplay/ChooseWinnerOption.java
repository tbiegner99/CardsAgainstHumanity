package com.tj.cardsagainsthumanity.client.options.types.gameplay;

import com.tj.cardsagainsthumanity.client.options.Option;
import com.tj.cardsagainsthumanity.client.utils.CardUtilities;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundBlackCard;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundCardPlay;

public class ChooseWinnerOption implements Option {
    private final RoundCardPlay play;
    private final RoundBlackCard blackCard;
    private final CardUtilities cardUtilities;

    public ChooseWinnerOption(RoundBlackCard blackCard, RoundCardPlay play) {
        this.play = play;
        this.blackCard = blackCard;
        this.cardUtilities = new CardUtilities();
    }

    public RoundCardPlay getPlay() {
        return play;
    }

    public RoundBlackCard getBlackCard() {
        return blackCard;
    }

    @Override
    public String getText() {
        return cardUtilities.generatePlayForCard(blackCard, play.getCards());
    }
}
