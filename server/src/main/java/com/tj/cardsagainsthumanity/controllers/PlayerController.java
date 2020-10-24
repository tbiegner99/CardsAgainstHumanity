package com.tj.cardsagainsthumanity.controllers;

import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.security.auth.PlayerUserDetails;
import com.tj.cardsagainsthumanity.serializer.RequestConverter;
import com.tj.cardsagainsthumanity.serializer.ResponseConverter;
import com.tj.cardsagainsthumanity.serializer.requestModel.user.PlayerRegistrationRequest;
import com.tj.cardsagainsthumanity.serializer.responseModel.gameplay.PlayerResponse;
import com.tj.cardsagainsthumanity.services.gameplay.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Optional;

@Controller
public class PlayerController {
    private PlayerService playerService;
    private RequestConverter<PlayerRegistrationRequest, Player> registrationRequestConverter;
    private ResponseConverter<Player, PlayerResponse> playerResponseConverter;

    public PlayerController(@Autowired PlayerService playerService, @Autowired RequestConverter<PlayerRegistrationRequest, Player> registrationRequestConverter, @Autowired ResponseConverter<Player, PlayerResponse> playerResponseConverter) {
        this.playerService = playerService;
        this.registrationRequestConverter = registrationRequestConverter;
        this.playerResponseConverter = playerResponseConverter;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/players")
    public ResponseEntity<PlayerResponse> registerPlayer(@RequestBody PlayerRegistrationRequest request) {
        Player playerToRegister = registrationRequestConverter.convertRequestToBusinessObject(request);
        Player responsePlayer = playerService.createPlayer(playerToRegister);
        PlayerResponse response = playerResponseConverter.convertBusinessObjectToResponse(responsePlayer);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);

    }


    @RequestMapping(method = RequestMethod.GET, value = "/auth/me")
    public ResponseEntity<PlayerResponse> getMyInfo(Authentication authentication) {
        PlayerUserDetails player = (PlayerUserDetails)authentication.getPrincipal();
        Player responsePlayer = player.getPlayer();
        PlayerResponse response = playerResponseConverter.convertBusinessObjectToResponse(responsePlayer);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);

    }

}
