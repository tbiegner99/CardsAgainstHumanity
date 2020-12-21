package com.tj.cardsagainsthumanity.controllers;

import com.tj.cardsagainsthumanity.models.cards.BlackCard;
import com.tj.cardsagainsthumanity.models.cards.WhiteCard;
import com.tj.cardsagainsthumanity.serializer.converter.card.CardResponseConverter;
import com.tj.cardsagainsthumanity.serializer.converter.card.CreateBlackCardRequestConverter;
import com.tj.cardsagainsthumanity.serializer.converter.card.CreateWhiteCardRequestConverter;
import com.tj.cardsagainsthumanity.serializer.requestModel.card.CreateCardRequest;
import com.tj.cardsagainsthumanity.serializer.responseModel.cardPackage.CardResponse;
import com.tj.cardsagainsthumanity.services.CardPackageService;
import com.tj.cardsagainsthumanity.services.CardService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CardControllerTest {


    @Mock
    private CardResponseConverter cardResponseConverter;
    @Mock
    private CreateCardRequest createCardRequest;
    @Mock
    private CardResponse response;
    @Mock
    private BlackCard convertedBlack;
    @Mock
    private WhiteCard convertedWhite;
    @Mock
    private CardService cardService;
    @Mock
    private CreateBlackCardRequestConverter createBlackCardRequestConverter;
    @Mock
    private CreateWhiteCardRequestConverter createWhiteCardRequestConverter;
    @Mock
    private Authentication authentication;
    @Mock
    private CardPackageService cardPackageService;

    private CardController controller;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(cardService.createCard(1, convertedBlack)).thenReturn(convertedBlack);
        when(cardService.createCard(1, convertedWhite)).thenReturn(convertedWhite);
        when(createBlackCardRequestConverter.convertRequestToBusinessObject(createCardRequest)).thenReturn(convertedBlack);
        when(createWhiteCardRequestConverter.convertRequestToBusinessObject(createCardRequest)).thenReturn(convertedWhite);
        when(cardResponseConverter.convertBusinessObjectToResponse(any())).thenReturn(response);

        controller = new CardController(cardPackageService, cardService, cardResponseConverter, createBlackCardRequestConverter, createWhiteCardRequestConverter);

    }

    @Test
    public void createBlackCard() {
        ResponseEntity<CardResponse> result = controller.createBlackCard(authentication, 2, createCardRequest);
        //it converts serializer to business object
        verify(createBlackCardRequestConverter, times(1)).convertRequestToBusinessObject(createCardRequest);
        //it calls the service
        verify(cardService, times(1)).createCard(1, convertedBlack);
        //it converts bo to response
        verify(cardResponseConverter, times(1)).convertBusinessObjectToResponse(convertedBlack);
        //it sets the correct body
        assertSame(result.getBody(), response);

    }

    @Test
    public void createWhiteCard() {
        ResponseEntity<CardResponse> result = controller.createWhiteCard(authentication, 2, createCardRequest);
        //it converts serializer to business object
        verify(createWhiteCardRequestConverter, times(1)).convertRequestToBusinessObject(createCardRequest);
        //it calls the service
        verify(cardService, times(1)).createCard(1, convertedWhite);
        //it converts bo to response
        verify(cardResponseConverter, times(1)).convertBusinessObjectToResponse(convertedWhite);
        //it sets the correct body
        assertSame(result.getBody(), response);

    }
}