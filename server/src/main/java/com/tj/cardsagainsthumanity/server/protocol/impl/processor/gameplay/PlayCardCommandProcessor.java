package com.tj.cardsagainsthumanity.server.protocol.impl.processor.gameplay;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.dao.CardDao;
import com.tj.cardsagainsthumanity.models.cards.WhiteCard;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.server.protocol.CommandProcessor;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundWhiteCard;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.gameplay.PlayCardCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.EmptyResponse;
import com.tj.cardsagainsthumanity.server.protocol.message.CommandContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Component
public class PlayCardCommandProcessor implements CommandProcessor<PlayCardCommand, EmptyResponse> {
    private CardDao cardDao;

    @Autowired
    public PlayCardCommandProcessor(CardDao cardDao) {
        this.cardDao = cardDao;
    }

    @Override
    @Transactional
    public EmptyResponse processMessage(PlayCardCommand messageToProcess, CommandContext context) {
        try {
            GameDriver game = context.getCurrentGame().get();
            Player player = context.getPlayer().get();
            List<WhiteCard> cards = loadWhiteCardsFrom(messageToProcess.getArguments().getCardsToPlay());
            game.getCurrentRound().playCards(player, cards);

            return EmptyResponse.NO_CONTENT.forMessage(messageToProcess.getMessageId());
        } catch (NoSuchElementException ex) {
            return EmptyResponse.FORBIDDEN.forMessage(messageToProcess.getMessageId());
        }
    }

    private List<WhiteCard> loadWhiteCardsFrom(List<RoundWhiteCard> cardsToPlay) {
        return cardsToPlay.stream()
                .map(RoundWhiteCard::getId)
                .map(cardDao::getWhiteCard)
                .collect(Collectors.toList());
    }
}
