package com.tj.cardsagainsthumanity.controllers;

import com.tj.cardsagainsthumanity.models.bulk.BulkReport;
import com.tj.cardsagainsthumanity.models.cards.Card;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.security.auth.PlayerUserDetails;
import com.tj.cardsagainsthumanity.serializer.converter.card.CardResponseConverter;
import com.tj.cardsagainsthumanity.serializer.converter.card.CreateBlackCardRequestConverter;
import com.tj.cardsagainsthumanity.serializer.converter.card.CreateWhiteCardRequestConverter;
import com.tj.cardsagainsthumanity.serializer.requestModel.card.CreateCardRequest;
import com.tj.cardsagainsthumanity.serializer.responseModel.cardPackage.CardResponse;
import com.tj.cardsagainsthumanity.services.CardPackageService;
import com.tj.cardsagainsthumanity.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CardController {

    private CardService cardService;
    private CardPackageService packageService;
    private CardResponseConverter cardResponseConverter;
    private CreateBlackCardRequestConverter createBlackCardRequestConverter;
    private CreateWhiteCardRequestConverter createWhiteCardRequestConverter;

    public CardController(@Autowired CardPackageService cardPackageService, @Autowired CardService cardService, @Autowired CardResponseConverter cardResponseConverter, @Autowired CreateBlackCardRequestConverter createBlackCardRequestConverter, @Autowired CreateWhiteCardRequestConverter createWhiteCardRequestConverter) {
        this.cardService = cardService;
        this.packageService = cardPackageService;
        this.cardResponseConverter = cardResponseConverter;
        this.createBlackCardRequestConverter = createBlackCardRequestConverter;
        this.createWhiteCardRequestConverter = createWhiteCardRequestConverter;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/packages/{packageId}/blackCards")
    public ResponseEntity<CardResponse> createBlackCard(Authentication authentication, @PathVariable("packageId") Integer packageId, @RequestBody CreateCardRequest request) {
        PlayerUserDetails player = (PlayerUserDetails) authentication.getPrincipal();
        Player owner = player.getPlayer();
        packageService.assertCanEditPackage(owner, packageId);
        request.setPackageId(packageId);
        Card card = createBlackCardRequestConverter.convertRequestToBusinessObject(request);
        return createCard(packageId, card);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/packages/{packageId}/whiteCards")
    public ResponseEntity<CardResponse> createWhiteCard(Authentication authentication, @PathVariable("packageId") Integer packageId, @RequestBody CreateCardRequest request) {
        PlayerUserDetails player = (PlayerUserDetails) authentication.getPrincipal();
        Player owner = player.getPlayer();
        packageService.assertCanEditPackage(owner, packageId);
        request.setPackageId(packageId);
        Card card = createWhiteCardRequestConverter.convertRequestToBusinessObject(request);
        return createCard(packageId, card);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/packages/{packageId}/blackCards/bulk")
    public ResponseEntity<?> createBlackCardsBulk(Authentication authentication, @PathVariable("packageId") Integer packageId, @RequestBody List<CreateCardRequest> request) {
        PlayerUserDetails player = (PlayerUserDetails) authentication.getPrincipal();
        Player owner = player.getPlayer();
        packageService.assertCanEditPackage(owner, packageId);
        request.forEach((req) -> req.setPackageId(packageId));
        List<Card> cards = request.stream()
                .collect(Collectors.toSet())
                .stream()
                .map(createBlackCardRequestConverter::convertRequestToBusinessObject)
                .collect(Collectors.toList());

        return createBulkCards(packageId, cards);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/packages/{packageId}/whiteCards/bulk")
    public ResponseEntity<?> createWhiteCardsBulk(Authentication authentication, @PathVariable("packageId") Integer packageId, @RequestBody List<CreateCardRequest> request) {
        PlayerUserDetails player = (PlayerUserDetails) authentication.getPrincipal();
        Player owner = player.getPlayer();
        packageService.assertCanEditPackage(owner, packageId);
        request.forEach((req) -> req.setPackageId(packageId));
        List<Card> cards = request.stream()
                .collect(Collectors.toSet())
                .stream()
                .map(createWhiteCardRequestConverter::convertRequestToBusinessObject)
                .collect(Collectors.toList());
        return createBulkCards(packageId, cards);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/packages/{packageId}/blackCards/{cardId}")
    public ResponseEntity<?> updateBlackCard(Authentication authentication, @PathVariable("packageId") Integer packageId, @PathVariable("cardId") Integer cardId, @RequestBody CreateCardRequest request) {
        PlayerUserDetails player = (PlayerUserDetails) authentication.getPrincipal();
        Player owner = player.getPlayer();
        packageService.assertCanEditPackage(owner, packageId);
        cardService.updateBlackCard(packageId, cardId, request.getCardText());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @RequestMapping(method = RequestMethod.PUT, value = "/packages/{packageId}/whiteCards/{cardId}")
    public ResponseEntity<CardResponse> updateWhiteCard(Authentication authentication, @PathVariable("packageId") Integer packageId, @PathVariable("cardId") Integer cardId, @RequestBody CreateCardRequest request) {
        PlayerUserDetails player = (PlayerUserDetails) authentication.getPrincipal();
        Player owner = player.getPlayer();
        packageService.assertCanEditPackage(owner, packageId);
        cardService.updateWhiteCard(packageId, cardId, request.getCardText());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/packages/{packageId}/blackCards/{cardId}")
    public ResponseEntity<?> deleteBlackCard(Authentication authentication, @PathVariable("packageId") Integer packageId, @PathVariable("cardId") Integer cardId) {
        PlayerUserDetails player = (PlayerUserDetails) authentication.getPrincipal();
        Player owner = player.getPlayer();
        packageService.assertCanEditPackage(owner, packageId);
        cardService.deleteBlackCard(packageId, cardId);
        packageService.updateCardCountForPackage(packageId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/packages/{packageId}/whiteCards/{cardId}")
    public ResponseEntity<CardResponse> deleteWhiteCard(Authentication authentication, @PathVariable("packageId") Integer packageId, @PathVariable("cardId") Integer cardId) {
        PlayerUserDetails player = (PlayerUserDetails) authentication.getPrincipal();
        Player owner = player.getPlayer();
        packageService.assertCanEditPackage(owner, packageId);
        cardService.deleteWhiteCard(packageId, cardId);
        packageService.updateCardCountForPackage(packageId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private ResponseEntity<CardResponse> createCard(Integer packageId, Card convertedCard) {
        Card savedCard = cardService.createCard(packageId, convertedCard);
        Card refreshedCard = cardService.refresh(savedCard);
        CardResponse response = cardResponseConverter.convertBusinessObjectToResponse(savedCard);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    private ResponseEntity<?> createBulkCards(Integer packageId, List<Card> cards) {
        BulkReport results = cardService.bulkCreateCards(packageId, cards);
        HttpStatus status;
        if (results.getTotal() == results.getSuccesses()) {
            status = HttpStatus.OK;
        } else if (results.getSuccesses() > 0) {
            status = HttpStatus.MULTI_STATUS;
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(status)
                .body(results);
    }
}
