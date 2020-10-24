package com.tj.cardsagainsthumanity.core.game;

import com.tj.cardsagainsthumanity.models.cards.WhiteCard;
import com.tj.cardsagainsthumanity.models.gameplay.CardPlay;
import com.tj.cardsagainsthumanity.models.gameplay.GameRound;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.models.gameplay.game.Scoreboard;
import com.tj.cardsagainsthumanity.models.gameplay.game.Voter;

import java.util.Collection;
import java.util.List;

public interface RoundDriver {
    Scoreboard getScoresForPlayers();

    void playCards(CardPlay play);

    void playCards(Player player, List<WhiteCard> cards);

    void declareWinner(Player czar, CardPlay play);

    void declareWinner(Player czar, Integer playId);

    void endRound();

    boolean isRoundOver();

    void upvote(Voter voter, CardPlay play);

    void downvote(Voter voter, CardPlay play);

    GameRound getRound();

    Collection<CardPlay> getAllCardPlays();

    void save();

    boolean areAllCardsIn();
}
