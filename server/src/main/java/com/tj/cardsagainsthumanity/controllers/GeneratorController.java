package com.tj.cardsagainsthumanity.controllers;

import com.tj.cardsagainsthumanity.models.gameplay.GameRound;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.security.http.resolvers.SessionService;
import com.tj.cardsagainsthumanity.serializer.converter.gameplay.GameRoundResponseConverter;
import com.tj.cardsagainsthumanity.serializer.responseModel.gameplay.GameRoundResponse;
import com.tj.cardsagainsthumanity.services.RandomizerService;
import com.tj.cardsagainsthumanity.services.gameplay.GameRoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class GeneratorController {

    private GameRoundService gameRoundService;
    private RandomizerService service;
    private SessionService playerResolver;
    private GameRoundResponseConverter gameRoundResponseConverter;

    public GeneratorController(@Autowired GameRoundService gameRoundService, @Autowired RandomizerService service, @Autowired SessionService playerResolver, @Autowired GameRoundResponseConverter gameRoundResponseConverter) {
        this.service = service;
        this.playerResolver = playerResolver;
        this.gameRoundService = gameRoundService;
        this.gameRoundResponseConverter = gameRoundResponseConverter;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/auth/generateRandomRound/{numberOfPlayers}")
    public ResponseEntity<?> getRandomCardRoundForNumberOfPlayers(HttpServletRequest request, @PathVariable("numberOfPlayers") Integer numberOfPlayers) {
        Player czar = playerResolver.resolveFrom(request);
        GameRound generatedRound = service.getRandomRoundForNumberOfPlayers(czar, numberOfPlayers);
        GameRound savedRound = gameRoundService.saveGameRound(generatedRound);
        GameRoundResponse response = gameRoundResponseConverter.convertBusinessObjectToResponse(savedRound);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
