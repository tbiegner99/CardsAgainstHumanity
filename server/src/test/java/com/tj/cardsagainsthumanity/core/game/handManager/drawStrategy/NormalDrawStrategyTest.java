package com.tj.cardsagainsthumanity.core.game.handManager.drawStrategy;

import com.tj.cardsagainsthumanity.dao.CardDao;
import com.tj.cardsagainsthumanity.models.cards.WhiteCard;
import com.tj.cardsagainsthumanity.models.gameplay.Game;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class NormalDrawStrategyTest {
    @Mock
    private CardDao cardDao;
    @Mock
    private Game game;
    private List<WhiteCard> drawnCards;
    private NormalDrawStrategy drawStrategy;

    @Before
    public void setUp() throws Exception {
        drawStrategy = new NormalDrawStrategy(cardDao, game);
        when(game.getId()).thenReturn(4);
        when(cardDao.getRandomWhiteCardsForGame(4, 6)).thenReturn(drawnCards);
    }


    @Test
    public void drawCards() {
        List<WhiteCard> cards = drawStrategy.drawCards(6);
        verify(cardDao, times(1)).getRandomWhiteCardsForGame(4, 6);
        assertSame(cards, drawnCards);

    }
}