package com.tj.cardsagainsthumanity.controllers;


import com.tj.cardsagainsthumanity.models.gameplay.GameRound;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.security.http.resolvers.SessionService;
import com.tj.cardsagainsthumanity.serializer.converter.gameplay.GameRoundResponseConverter;
import com.tj.cardsagainsthumanity.serializer.requestModel.gameplay.WinningCardPlayRequest;
import com.tj.cardsagainsthumanity.serializer.responseModel.gameplay.GameRoundResponse;
import com.tj.cardsagainsthumanity.services.gameplay.GameRoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class GameRoundController {
    private GameRoundService roundService;
    private SessionService playerResolver;
    private GameRoundResponseConverter responseConverter;

    public GameRoundController(@Autowired GameRoundService roundService, @Autowired SessionService playerResolver, @Autowired GameRoundResponseConverter responseConverter) {
        this.roundService = roundService;
        this.playerResolver = playerResolver;
        this.responseConverter = responseConverter;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/auth/gameRound/{roundId}/winningPlay")
    public ResponseEntity<?> declareWinnerForRound(HttpServletRequest request, @PathVariable("roundId") Integer roundId, @RequestBody WinningCardPlayRequest winnerRequest) {
        Player player = playerResolver.resolveFrom(request);

        roundService.chooseWinnerForGameRound(player, roundId, winnerRequest.getCardPlayId());

        return ResponseEntity.noContent()
                .build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/auth/gameRound/{roundId}")
    public ResponseEntity<GameRoundResponse> getGameRound(@PathVariable("roundId") Integer roundId) {
        GameRound round = roundService.getGameRound(roundId);
        GameRoundResponse response = responseConverter.convertBusinessObjectToResponse(round);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

}
