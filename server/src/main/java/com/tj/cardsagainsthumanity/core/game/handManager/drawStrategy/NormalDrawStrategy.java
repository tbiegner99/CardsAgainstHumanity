package com.tj.cardsagainsthumanity.core.game.handManager.drawStrategy;

import com.tj.cardsagainsthumanity.core.game.handManager.DrawStrategy;
import com.tj.cardsagainsthumanity.dao.CardDao;
import com.tj.cardsagainsthumanity.models.cards.WhiteCard;
import com.tj.cardsagainsthumanity.models.gameplay.Game;

import java.util.List;


public class NormalDrawStrategy implements DrawStrategy<WhiteCard> {
    private CardDao cardDao;
    private Game game;
    
    public NormalDrawStrategy(CardDao cardDao, Game game) {
        this.cardDao = cardDao;
        this.game = game;
    }

    @Override
    public List<WhiteCard> drawCards(int numToDraw) {
        return cardDao.getRandomWhiteCardsForGame(game.getId(), numToDraw);
    }
}
