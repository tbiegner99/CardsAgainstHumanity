package com.tj.cardsagainsthumanity.core.game.factory;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.core.game.RoundDriver;
import com.tj.cardsagainsthumanity.core.game.RoundType;
import com.tj.cardsagainsthumanity.core.game.events.EventFactory;
import com.tj.cardsagainsthumanity.core.game.events.dispatcher.GameEventDispatcher;
import com.tj.cardsagainsthumanity.core.game.factory.impl.RoundDriverFactory;
import com.tj.cardsagainsthumanity.core.game.gameDriver.CzarGenerator;
import com.tj.cardsagainsthumanity.core.game.roundDriver.NormalGameRoundDriver;
import com.tj.cardsagainsthumanity.dao.CardDao;
import com.tj.cardsagainsthumanity.dao.gameplay.GameRoundDao;
import com.tj.cardsagainsthumanity.models.cards.BlackCard;
import com.tj.cardsagainsthumanity.models.gameplay.Game;
import com.tj.cardsagainsthumanity.models.gameplay.GameRound;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RoundDriverFactoryTest {
    @Mock
    private CardDao cardDao;
    @Mock
    private GameRoundDao roundDao;
    @Mock
    private CzarGenerator czarGen;
    @Mock
    private BlackCard generatedCard;
    @Mock
    private Player generatedCzar;
    @Mock
    private GameDriver gameDriver;
    @Mock
    private Game mockGame;
    @Mock
    private GameEventDispatcher eventDispatcher;
    @Mock
    private EventFactory eventFactory;

    private RoundDriverFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new RoundDriverFactory(cardDao, roundDao);
        when(gameDriver.getGame()).thenReturn(mockGame);
        when(czarGen.next()).thenReturn(generatedCzar);
        when(cardDao.getRandomBlackCardForGame(any())).thenReturn(generatedCard);
    }

    @Test
    public void createGameRound() {
        RoundDriver result = factory.createGameRound(gameDriver, eventDispatcher, eventFactory, RoundType.NORMAL, czarGen);
        GameRound expectedRound = new GameRound(generatedCzar, generatedCard);
        expectedRound.setGame(mockGame);
        RoundDriver expectedResult = new NormalGameRoundDriver(gameDriver, eventDispatcher, eventFactory, expectedRound, roundDao);
        //it generates czargetItem("key"), "val2");
        verify(czarGen, times(1)).next();
        //it gets blackCard
        verify(cardDao, times(1)).getRandomBlackCardForGame(any());
        assertEquals(result, expectedResult);
    }
}