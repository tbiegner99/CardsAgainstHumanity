package com.tj.cardsagainsthumanity.serializer.converter.gameplay;

import com.tj.cardsagainsthumanity.models.cards.BlackCard;
import com.tj.cardsagainsthumanity.models.gameplay.CardPlay;
import com.tj.cardsagainsthumanity.models.gameplay.GameRound;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.serializer.converter.card.CardResponseConverter;
import com.tj.cardsagainsthumanity.serializer.responseModel.cardPackage.CardResponse;
import com.tj.cardsagainsthumanity.serializer.responseModel.gameplay.CardPlayResponse;
import com.tj.cardsagainsthumanity.serializer.responseModel.gameplay.GameRoundResponse;
import com.tj.cardsagainsthumanity.serializer.responseModel.gameplay.PlayerResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameRoundResponseConverterTest {
    @Mock
    private CardResponseConverter cardResponseConverter;
    @Mock
    private CardPlayResponseConverter cardPlayResponseConverter;
    @Mock
    private PlayerResponseConverter playerResponseConverter;
    @Mock
    private CardPlay play;
    @Mock
    private CardPlay play2;
    @Mock
    private BlackCard blackCard;
    @Mock
    private Player czar;
    @Mock
    private CardResponse blackCardResponse;
    @Mock
    private PlayerResponse playerResponse;
    @Mock
    private CardPlayResponse playResponse;

    private GameRound round;
    private GameRoundResponseConverter converter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        converter = new GameRoundResponseConverter(cardResponseConverter, playerResponseConverter, cardPlayResponseConverter);
        when(playerResponseConverter.convertBusinessObjectToResponse(czar)).thenReturn(playerResponse);
        when(cardPlayResponseConverter.convertBusinessObjectToResponse(any())).thenReturn(playResponse);
        when(cardResponseConverter.convertBusinessObjectToResponse(blackCard)).thenReturn(blackCardResponse);
        when(play2.getId()).thenReturn(89);
        
        round = new GameRound();
        round.setPlays(new HashSet<>(Arrays.asList(play, play2)));
        round.setBlackCard(blackCard);
        round.setCzar(czar);
        round.setWinner(play2);
        round.setId(4);
    }

    @Test
    public void convertBusinessObjectToResponse() {
        GameRoundResponse expectedResponse = new GameRoundResponse();
        expectedResponse.setId(4);
        expectedResponse.setBlackCard(blackCardResponse);
        expectedResponse.setCzar(playerResponse);
        expectedResponse.setPlays(Arrays.asList(playResponse, playResponse));
        expectedResponse.setWinnerId(89);
        GameRoundResponse response = converter.convertBusinessObjectToResponse(round);

        assertEquals(response, expectedResponse);

    }
}