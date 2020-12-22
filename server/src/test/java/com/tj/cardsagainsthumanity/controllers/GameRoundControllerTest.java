package com.tj.cardsagainsthumanity.controllers;

import com.tj.cardsagainsthumanity.models.gameplay.GameRound;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.security.http.resolvers.SessionService;
import com.tj.cardsagainsthumanity.serializer.converter.gameplay.GameRoundResponseConverter;
import com.tj.cardsagainsthumanity.serializer.requestModel.gameplay.WinningCardPlayRequest;
import com.tj.cardsagainsthumanity.serializer.responseModel.gameplay.GameRoundResponse;
import com.tj.cardsagainsthumanity.services.gameplay.GameRoundService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class GameRoundControllerTest {
    @Mock
    GameRoundService gameRoundService;
    @Mock
    SessionService sessionService;
    @Mock
    GameRound mockResult;
    @Mock
    GameRoundResponseConverter responseConverter;
    @Mock
    GameRoundResponse mockResponse;
    @Mock
    HttpServletRequest request;
    @Mock
    WinningCardPlayRequest winningPlayRequest;
    @Mock
    Player player;

    GameRoundController controller;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new GameRoundController(gameRoundService, sessionService, responseConverter);
        when(sessionService.resolveFrom(any())).thenReturn(player);
        winningPlayRequest = new WinningCardPlayRequest();
        winningPlayRequest.setCardPlayId(7);
        when(gameRoundService.getGameRound(89)).thenReturn(mockResult);
        when(responseConverter.convertBusinessObjectToResponse(mockResult)).thenReturn(mockResponse);
    }

    @Test
    public void declareWinnerForRound() {
        ResponseEntity<?> response = controller.declareWinnerForRound(request, 3, winningPlayRequest);
        verify(gameRoundService, times(1)).chooseWinnerForGameRound(player, 3, 7);
        assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);

    }

    @Test
    public void getGameRound() {
        ResponseEntity<GameRoundResponse> result = controller.getGameRound(89);
        verify(gameRoundService, times(1)).getGameRound(89);
        verify(responseConverter, times(1)).convertBusinessObjectToResponse(mockResult);
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertSame(result.getBody(), mockResponse);
    }
}