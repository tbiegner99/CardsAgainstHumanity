package com.tj.cardsagainsthumanity.server.protocol.impl.processor.gameplay;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.dao.CardDao;
import com.tj.cardsagainsthumanity.models.cards.WhiteCard;
import com.tj.cardsagainsthumanity.models.gameStatus.GameStatus;
import com.tj.cardsagainsthumanity.models.gameStatus.GameStatusFactory;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.server.protocol.CommandProcessor;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundWhiteCard;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.gameplay.PlayCardCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.EmptyResponse;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.GameResponse;
import com.tj.cardsagainsthumanity.server.protocol.message.CommandContext;
import com.tj.cardsagainsthumanity.server.protocol.message.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Component
public class PlayCardCommandProcessor implements CommandProcessor<PlayCardCommand, Response<?>> {
    private final CardDao cardDao;
    private GameStatusFactory statusFactory;

    @Autowired
    public PlayCardCommandProcessor(CardDao cardDao, GameStatusFactory statusFactory) {
        this.statusFactory = statusFactory;
        this.cardDao = cardDao;
    }


    @Override
    @Transactional
    public Response<?> processMessage(PlayCardCommand messageToProcess, CommandContext context) {
        try {
            GameDriver game = context.getCurrentGame().get();
            Player player = context.getPlayer().get();
            List<WhiteCard> cards = loadWhiteCardsFrom(messageToProcess.getArguments().getCardsToPlay());
            game.getCurrentRound().playCards(player, cards);
            GameStatus status = statusFactory.buildGameStatus(player, game);
            return new GameResponse(messageToProcess.getMessageId(), status);
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
