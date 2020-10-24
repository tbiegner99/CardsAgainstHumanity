package com.tj.cardsagainsthumanity.server.protocol.impl.processor;

import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.security.auth.PlayerUserDetails;
import com.tj.cardsagainsthumanity.security.service.LoginService;
import com.tj.cardsagainsthumanity.server.protocol.CommandProcessor;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.LoginCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.LoginInfo;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.EmptyResponse;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.SuccessfulLoginResponse;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.body.LoginResponseBody;
import com.tj.cardsagainsthumanity.server.protocol.message.CommandContext;
import com.tj.cardsagainsthumanity.server.protocol.message.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class LoginCommandProcessor implements CommandProcessor<LoginCommand, Response<LoginResponseBody>> {
    private LoginService loginService;

    @Autowired
    public LoginCommandProcessor(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public Response<LoginResponseBody> processMessage(LoginCommand messageToProcess, CommandContext context) {
        Optional<Player> player = checkLogin(messageToProcess.getArguments());
        String messageId = messageToProcess.getMessageId();
        if (player.isPresent()) {
            return getLoginResponseForSuccessfulLogin(context, player, messageId);
        }
        return EmptyResponse.FORBIDDEN.forMessage(messageId);
    }

    private Response<LoginResponseBody> getLoginResponseForSuccessfulLogin(CommandContext context, Optional<Player> player, String messageId) {
        Player result = player.get();
        context.login(result);
        LoginResponseBody response = new LoginResponseBody(result.getId(), UUID.randomUUID().toString());
        return new SuccessfulLoginResponse(messageId, response);
    }

    private Optional<Player> checkLogin(LoginInfo loginInfo) {
        try {
            SecurityContext securityContext = loginService.login(loginInfo.getUsername(), loginInfo.getPassword());
            Authentication authentication = securityContext.getAuthentication();
            PlayerUserDetails player = (PlayerUserDetails) authentication.getPrincipal();

            return Optional.of(player.getPlayer());
        } catch (Exception ex) {
            return Optional.empty();
        }
    }
}
