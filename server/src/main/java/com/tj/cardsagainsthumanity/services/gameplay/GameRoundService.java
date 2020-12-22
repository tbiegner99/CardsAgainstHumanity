package com.tj.cardsagainsthumanity.services.gameplay;

import com.tj.cardsagainsthumanity.dao.gameplay.CardPlayDao;
import com.tj.cardsagainsthumanity.dao.gameplay.GameRoundDao;
import com.tj.cardsagainsthumanity.exceptions.NoRowUpdatedException;
import com.tj.cardsagainsthumanity.models.cards.WhiteCard;
import com.tj.cardsagainsthumanity.models.gameplay.CardPlay;
import com.tj.cardsagainsthumanity.models.gameplay.GameRound;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class GameRoundService {

    private GameRoundDao gameRoundDao;

    private CardPlayDao cardPlayDao;

    @Autowired
    public GameRoundService(GameRoundDao gameRoundDao, CardPlayDao cardPlayDao) {
        this.gameRoundDao = gameRoundDao;
        this.cardPlayDao = cardPlayDao;
    }

    public GameRound playCardsForRound(Integer roundId, Player player, List<WhiteCard> cards) {
        GameRound round = gameRoundDao.getGameRound(roundId);
        Integer numberOfAnswers = round.getBlackCard().getNumberOfAnswers();
        if (numberOfAnswers != cards.size()) {
            throw new IllegalArgumentException("Invalid number of cards played.");
        }

        CardPlay play = new CardPlay(round, player, cards);
        round.addCardPlay(play);

        cardPlayDao.saveCardPlay(play);
        return gameRoundDao.saveGameRound(round);
    }


    public GameRound saveGameRound(GameRound round) {
        return gameRoundDao.saveGameRound(round);
    }

    public GameRound chooseWinnerForGameRound(Player player, Integer gameRoundId, Integer cardPlayId) {
        try {
            GameRound round = gameRoundDao.getGameRound(gameRoundId);
            if (!player.isCzarFor(round)) {
                throw new IllegalArgumentException("You are not the czar for this round");
            }

            gameRoundDao.setWinningPlayForRound(gameRoundId, cardPlayId);
            cardPlayDao.makeCardPlayWinner(cardPlayId);
            return gameRoundDao.getGameRound(gameRoundId);
        } catch (NoRowUpdatedException e) {
            throw new IllegalArgumentException("Card play " + cardPlayId + " is not a play for round " + gameRoundId);
        }

    }

    public GameRound getGameRound(Integer roundId) {
        return gameRoundDao.getGameRound(roundId);
    }
}
