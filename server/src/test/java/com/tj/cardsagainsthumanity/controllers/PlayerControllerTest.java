package com.tj.cardsagainsthumanity.controllers;

import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.serializer.converter.user.PlayerRegistrationRequestConverter;
import com.tj.cardsagainsthumanity.serializer.converter.gameplay.PlayerResponseConverter;
import com.tj.cardsagainsthumanity.serializer.requestModel.dbImport.DatabaseImportRequest;
import com.tj.cardsagainsthumanity.serializer.requestModel.user.PlayerRegistrationRequest;
import com.tj.cardsagainsthumanity.serializer.responseModel.gameplay.PlayerResponse;
import com.tj.cardsagainsthumanity.services.gameplay.PlayerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlayerControllerTest {
    @Mock
    private PlayerRegistrationRequestConverter registrationRequestConverter;
    @Mock
    private PlayerResponseConverter playerResponseConverter;
    @Mock
    private PlayerService playerService;
    @Mock
    private PlayerRegistrationRequest fakeRequest;
    @Mock
    private Player testPlayer;
    @Mock
    private Player responsePlayer;
    @Mock
    private PlayerResponse mockResponse;

    private DatabaseImportRequest importRequest;
    private PlayerController controller;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new PlayerController(playerService, registrationRequestConverter, playerResponseConverter);
        when(registrationRequestConverter.convertRequestToBusinessObject(fakeRequest)).thenReturn(testPlayer);
        when(playerService.createPlayer(testPlayer)).thenReturn(responsePlayer);
        when(playerResponseConverter.convertBusinessObjectToResponse(responsePlayer)).thenReturn(mockResponse);
    }

    @Test
    public void registerPlayer() {
        ResponseEntity<PlayerResponse> result = controller.registerPlayer(fakeRequest);
        //it converts serializer to business object
        verify(registrationRequestConverter, times(1)).convertRequestToBusinessObject(fakeRequest);
        //it calls the service
        verify(playerService, times(1)).createPlayer(testPlayer);
        //it converts bo to response
        verify(playerResponseConverter, times(1)).convertBusinessObjectToResponse(responsePlayer);
        //it sets the correct body
        assertSame(result.getBody(), mockResponse);
        assertEquals(result.getStatusCode(), HttpStatus.CREATED);
    }


}