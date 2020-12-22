package com.tj.cardsagainsthumanity.services;

import com.tj.cardsagainsthumanity.dao.CardDao;
import com.tj.cardsagainsthumanity.models.cards.BlackCard;
import com.tj.cardsagainsthumanity.models.cards.WhiteCard;
import com.tj.cardsagainsthumanity.models.gameplay.GameRound;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class RandomizerService {

    private CardDao dao;

    @Autowired
    public RandomizerService(CardDao dao) {
        this.dao = dao;
    }

    public BlackCard getRandomBlackCard() {
        return dao.getRandomBlackCard();
    }

    public WhiteCard getRandomWhiteCard() {
        return dao.getRandomWhiteCards(1).get(0);
    }

    public GameRound getRandomRoundForNumberOfPlayers(Player czar, int numberofPlayers) {
        BlackCard blackCard = this.getRandomBlackCard();
        GameRound round = new GameRound(czar, blackCard);
        int numberOfAnswersPerPlayer = blackCard.getNumberOfAnswers();
        List<WhiteCard> playedCards = dao.getRandomWhiteCards(numberofPlayers * numberOfAnswersPerPlayer);
        Iterator<WhiteCard> cardsIterator = playedCards.iterator();
        playCards(round, cardsIterator, numberofPlayers, numberOfAnswersPerPlayer);

        return round;
    }

    private void playCards(GameRound round, Iterator<WhiteCard> cardsIterator, int numberofPlayers, int numberOfAnswersPerPlayer) {
        for (int player = 0; player < numberofPlayers; player++) {
            playCardsForPlayer(round, cardsIterator, numberOfAnswersPerPlayer);
        }
    }

    private void playCardsForPlayer(GameRound round, Iterator<WhiteCard> cardsIterator, int numberOfAnswersPerPlayer) {
        List<WhiteCard> answers = new ArrayList<>();
        for (int answer = 0; answer < numberOfAnswersPerPlayer; answer++) {
            answers.add(cardsIterator.next());
        }
        round.playCardsAnonomously(answers);
    }


}
