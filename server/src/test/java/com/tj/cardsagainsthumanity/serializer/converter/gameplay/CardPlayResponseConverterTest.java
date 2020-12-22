package com.tj.cardsagainsthumanity.serializer.converter.gameplay;

import com.tj.cardsagainsthumanity.models.cards.WhiteCard;
import com.tj.cardsagainsthumanity.models.gameplay.CardPlay;
import com.tj.cardsagainsthumanity.models.gameplay.GameRound;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.serializer.converter.card.CardResponseConverter;
import com.tj.cardsagainsthumanity.serializer.responseModel.cardPackage.CardResponse;
import com.tj.cardsagainsthumanity.serializer.responseModel.gameplay.CardPlayResponse;
import com.tj.cardsagainsthumanity.serializer.responseModel.gameplay.PlayerResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CardPlayResponseConverterTest {
    @Mock
    private PlayerResponseConverter playerConverter;
    @Mock
    private CardResponseConverter cardConverter;
    @Mock
    private GameRound gameRound;
    @Mock
    private WhiteCard card;
    @Mock
    private Player player;
    @Mock
    private CardResponse cardResponse;
    @Mock
    private PlayerResponse playerResponse;

    private CardPlay cardPlay;

    private CardPlayResponseConverter converter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        converter = new CardPlayResponseConverter(playerConverter, cardConverter);
        when(playerConverter.convertBusinessObjectToResponse(player)).thenReturn(playerResponse);
        when(cardConverter.convertBusinessObjectToResponse(card)).thenReturn(cardResponse);
        when(gameRound.getId()).thenReturn(34);

        cardPlay = new CardPlay();
        cardPlay.setRound(gameRound);
        cardPlay.setCards(Arrays.asList(card));
        cardPlay.setPlayer(player);
        cardPlay.setId(3);
        cardPlay.setWinner(true);
    }

    @Test
    public void convertBusinessObjectToResponse() {
        CardPlayResponse expectedResponse = new CardPlayResponse();
        expectedResponse.setId(3);
        expectedResponse.setRoundId(34);
        expectedResponse.setCards(Arrays.asList(cardResponse));
        expectedResponse.setPlayer(playerResponse);
        expectedResponse.setWinner(true);
        CardPlayResponse response = converter.convertBusinessObjectToResponse(cardPlay);
        assertEquals(response, expectedResponse);
    }
}