package com.tj.cardsagainsthumanity.controllers;

import com.tj.cardsagainsthumanity.models.gameplay.GameRound;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.security.http.resolvers.SessionService;
import com.tj.cardsagainsthumanity.serializer.converter.gameplay.GameRoundResponseConverter;
import com.tj.cardsagainsthumanity.serializer.responseModel.gameplay.GameRoundResponse;
import com.tj.cardsagainsthumanity.services.RandomizerService;
import com.tj.cardsagainsthumanity.services.gameplay.GameRoundService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GeneratorControllerTest {

    @Mock
    private RandomizerService service;
    @Mock
    private Player czar;
    @Mock
    private SessionService resolver;
    @Mock
    private HttpServletRequest request;
    @Mock
    private GameRoundResponseConverter serializer;
    @Mock
    private GameRound roundObject;
    @Mock
    private GameRound savedRound;
    @Mock
    private GameRoundService gameRoundService;
    @Mock
    private GameRoundResponse mockResponse;

    private GeneratorController controller;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new GeneratorController(gameRoundService, service, resolver, serializer);
        when(resolver.resolveFrom(request)).thenReturn(czar);
        when(service.getRandomRoundForNumberOfPlayers(czar, 3)).thenReturn(roundObject);
        when(serializer.convertBusinessObjectToResponse(savedRound)).thenReturn(mockResponse);
        when(gameRoundService.saveGameRound(roundObject)).thenReturn(savedRound);

    }

    @Test
    public void getRandomCardRoundForNumberOfPlayers() {
        ResponseEntity<?> result = controller.getRandomCardRoundForNumberOfPlayers(request, 3);
        //it resolves the player form the sesison
        verify(resolver, times(1)).resolveFrom(request);
        //it invokes ranomizer service
        verify(service, times(1)).getRandomRoundForNumberOfPlayers(czar, 3);
        //it serializes the response
        verify(serializer, times(1)).convertBusinessObjectToResponse(savedRound);
        //it saves the round
        verify(gameRoundService, times(1)).saveGameRound(roundObject);
        //it returns expected result
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertSame(result.getBody(), mockResponse);


    }
}