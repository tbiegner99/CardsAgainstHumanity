package com.tj.cardsagainsthumanity.controllers;

import com.tj.cardsagainsthumanity.models.cards.CardType;
import com.tj.cardsagainsthumanity.models.cards.DeckDetails;
import com.tj.cardsagainsthumanity.models.cards.DeckInfo;
import com.tj.cardsagainsthumanity.security.auth.PlayerUserDetails;
import com.tj.cardsagainsthumanity.serializer.converter.deck.CreateDeckRequestConverter;
import com.tj.cardsagainsthumanity.serializer.converter.deck.DeckResponseConverter;
import com.tj.cardsagainsthumanity.serializer.converter.deck.DetailedDeckResponseConverter;
import com.tj.cardsagainsthumanity.serializer.requestModel.deck.CardDeckEntry;
import com.tj.cardsagainsthumanity.serializer.requestModel.deck.CreateDeckRequest;
import com.tj.cardsagainsthumanity.serializer.requestModel.deck.PackageDeckEntry;
import com.tj.cardsagainsthumanity.serializer.responseModel.deck.DeckResponse;
import com.tj.cardsagainsthumanity.services.deck.DeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class DeckController {

    private DeckService deckService;
    private DeckResponseConverter deckResponseConverter;
    private DetailedDeckResponseConverter detailedDeckResponseConverter;
    private CreateDeckRequestConverter createDeckRequestConverter;

    public DeckController(@Autowired DeckService deckService, @Autowired DetailedDeckResponseConverter detailedDeckResponseConverter, @Autowired DeckResponseConverter deckResponseConverter, @Autowired CreateDeckRequestConverter createDeckRequestConverter) {
        this.deckService = deckService;
        this.deckResponseConverter = deckResponseConverter;
        this.detailedDeckResponseConverter = detailedDeckResponseConverter;
        this.createDeckRequestConverter = createDeckRequestConverter;
    }


    @RequestMapping(method = RequestMethod.POST, value = "/deck")
    public ResponseEntity<?> createDeck(Authentication authentication, @Valid @RequestBody CreateDeckRequest request) {
        PlayerUserDetails player = (PlayerUserDetails) authentication.getPrincipal();
        request.setOwner(player.getPlayer());
        DeckInfo deck = createDeckRequestConverter.convertRequestToBusinessObject(request);
        DeckInfo result = deckService.createDeck(deck);
        DeckResponse response = deckResponseConverter.convertBusinessObjectToResponse(result);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/deck/{deckId}")
    public ResponseEntity<?> getFullDeckInfo(Authentication authentication, @PathVariable("deckId") Integer deckId) {
        PlayerUserDetails player = (PlayerUserDetails) authentication.getPrincipal();
        deckService.assertUserCanEdit(player.getPlayer(), deckId);
        DeckDetails result = deckService.loadDeckDetails(deckId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(result);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deck/{deckId}")
    public ResponseEntity<?> deleteDeck(Authentication authentication, @PathVariable("deckId") Integer deckId) {
        PlayerUserDetails player = (PlayerUserDetails) authentication.getPrincipal();
        deckService.assertUserCanEdit(player.getPlayer(), deckId);
        deckService.deleteDeck(deckId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @RequestMapping(method = RequestMethod.POST, value = "/deck/{deckId}/package")
    public ResponseEntity<?> addPackage(Authentication authentication, @PathVariable("deckId") Integer deckId, @Valid @RequestBody PackageDeckEntry request) {
        PlayerUserDetails player = (PlayerUserDetails) authentication.getPrincipal();
        deckService.assertUserCanEdit(player.getPlayer(), deckId);
        DeckInfo result = deckService.addPackageToDeck(deckId, request.getPackageId());
        DeckResponse response = deckResponseConverter.convertBusinessObjectToResponse(result);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deck/{deckId}/package/{packageId}")
    public ResponseEntity<?> removePackage(Authentication authentication, @PathVariable("deckId") Integer deckId, @PathVariable("packageId") Integer packageId) {
        PlayerUserDetails player = (PlayerUserDetails) authentication.getPrincipal();
        deckService.assertUserCanEdit(player.getPlayer(), deckId);
        DeckInfo result = deckService.removePackageFromDeck(deckId, packageId);
        DeckResponse response = deckResponseConverter.convertBusinessObjectToResponse(result);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/deck/{deckId}/blackCard")
    public ResponseEntity<?> addBlackCard(Authentication authentication, @PathVariable("deckId") Integer deckId, @Valid @RequestBody CardDeckEntry request) {
        PlayerUserDetails player = (PlayerUserDetails) authentication.getPrincipal();
        deckService.assertUserCanEdit(player.getPlayer(), deckId);
        DeckInfo result = deckService.addCardToDeck(deckId, CardType.BLACK, request.getPackageId(), request.getCardId());
        DeckResponse response = deckResponseConverter.convertBusinessObjectToResponse(result);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/deck/{deckId}/blackCard/{cardId}")
    public ResponseEntity<?> removeBlackCard(Authentication authentication, @PathVariable("deckId") Integer deckId, @PathVariable("cardId") Integer cardId) {
        PlayerUserDetails player = (PlayerUserDetails) authentication.getPrincipal();
        deckService.assertUserCanEdit(player.getPlayer(), deckId);
        DeckInfo result = deckService.removeCardFromDeck(deckId, CardType.BLACK, cardId);
        DeckResponse response = deckResponseConverter.convertBusinessObjectToResponse(result);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/deck/{deckId}/whiteCard")
    public ResponseEntity<?> addWhiteCard(Authentication authentication, @PathVariable("deckId") Integer deckId, @Valid @RequestBody CardDeckEntry request) {
        PlayerUserDetails player = (PlayerUserDetails) authentication.getPrincipal();
        deckService.assertUserCanEdit(player.getPlayer(), deckId);
        DeckInfo result = deckService.addCardToDeck(deckId, CardType.WHITE, request.getPackageId(), request.getCardId());
        DeckResponse response = deckResponseConverter.convertBusinessObjectToResponse(result);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/deck/{deckId}/whiteCard/{cardId}")
    public ResponseEntity<?> removeWhiteCard(Authentication authentication, @PathVariable("deckId") Integer deckId, @PathVariable("cardId") Integer cardId) {
        PlayerUserDetails player = (PlayerUserDetails) authentication.getPrincipal();
        deckService.assertUserCanEdit(player.getPlayer(), deckId);
        DeckInfo result = deckService.removeCardFromDeck(deckId, CardType.WHITE, cardId);
        DeckResponse response = deckResponseConverter.convertBusinessObjectToResponse(result);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/deck")
    public ResponseEntity<?> getPlayableDecks(Authentication authentication) {
        PlayerUserDetails player = (PlayerUserDetails) authentication.getPrincipal();
        List<DeckInfo> result = deckService.getPlayableDecksForPlayer(player.getPlayer());
        List<DeckResponse> response = result.stream().map(deckResponseConverter::convertBusinessObjectToResponse).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/deck/me")
    public ResponseEntity<?> getEditableDecks(Authentication authentication) {
        PlayerUserDetails player = (PlayerUserDetails) authentication.getPrincipal();
        List<DeckInfo> result = deckService.getEditableDecksForPlayer(player.getPlayer());
        List<DeckResponse> response = result.stream().map(deckResponseConverter::convertBusinessObjectToResponse).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

}
