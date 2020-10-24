package com.tj.cardsagainsthumanity.models.gameplay;

import com.tj.cardsagainsthumanity.models.cards.BlackCard;
import com.tj.cardsagainsthumanity.models.cards.WhiteCard;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class GameRoundTest {
    @Mock
    private WhiteCard expectedWhite;

    @Mock
    private BlackCard blackCard;

    @Mock
    private Player czar;

    @Mock
    private Player player;

    private GameRound round;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        round = new GameRound(czar, blackCard);
    }

    @Test
    public void playCardAnonomously() {
        CardPlay play = new CardPlay(round, Player.ANONYMOUS, Arrays.asList(expectedWhite));

        CardPlay result = round.playCardsAnonomously(Arrays.asList(expectedWhite));
        //it plays card as null player
        assertEquals(round.getPlays().size(), 1);
        //it returns play
        assertEquals(result, play);
    }

    @Test
    public void playWhiteCard() {
        CardPlay play = new CardPlay(round, player, Arrays.asList(expectedWhite));

        CardPlay result = round.playWhiteCards(player, Arrays.asList(expectedWhite));
        //it plays card as null player
        assertEquals(round.getPlays().size(), 1);
        //it returns play
        assertEquals(result, play);

    }
}