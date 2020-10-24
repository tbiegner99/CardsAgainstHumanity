package com.tj.cardsagainsthumanity.controllers;

import com.tj.cardsagainsthumanity.models.cards.Card;
import com.tj.cardsagainsthumanity.serializer.converter.card.CardResponseConverter;
import com.tj.cardsagainsthumanity.serializer.converter.card.CreateBlackCardRequestConverter;
import com.tj.cardsagainsthumanity.serializer.converter.card.CreateWhiteCardRequestConverter;
import com.tj.cardsagainsthumanity.serializer.requestModel.card.CreateCardRequest;
import com.tj.cardsagainsthumanity.serializer.responseModel.cardPackage.CardResponse;
import com.tj.cardsagainsthumanity.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CardController {

    private CardService cardService;
    private CardResponseConverter cardResponseConverter;
    private CreateBlackCardRequestConverter createBlackCardRequestConverter;
    private CreateWhiteCardRequestConverter createWhiteCardRequestConverter;

    public CardController(@Autowired CardService cardService, @Autowired CardResponseConverter cardResponseConverter, @Autowired CreateBlackCardRequestConverter createBlackCardRequestConverter, @Autowired CreateWhiteCardRequestConverter createWhiteCardRequestConverter) {
        this.cardService = cardService;
        this.cardResponseConverter = cardResponseConverter;
        this.createBlackCardRequestConverter = createBlackCardRequestConverter;
        this.createWhiteCardRequestConverter = createWhiteCardRequestConverter;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/packages/{packageId}/blackCards")
    public ResponseEntity<CardResponse> createBlackCard(@PathVariable("packageId") Integer packageId, @RequestBody CreateCardRequest request) {
        request.setPackageId(packageId);
        Card convertedCard = createBlackCardRequestConverter.convertRequestToBusinessObject(request);
        return createCard(convertedCard);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/packages/{packageId}/whiteCards")
    public ResponseEntity<CardResponse> createWhiteCard(@PathVariable("packageId") Integer packageId, @RequestBody CreateCardRequest request) {
        request.setPackageId(packageId);
        Card convertedCard = createWhiteCardRequestConverter.convertRequestToBusinessObject(request);
        return createCard(convertedCard);
    }

    private ResponseEntity<CardResponse> createCard(Card convertedCard) {
        Card savedCard = cardService.createCard(convertedCard);

        CardResponse response = cardResponseConverter.convertBusinessObjectToResponse(savedCard);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }
}
