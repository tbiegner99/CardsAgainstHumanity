package com.tj.cardsagainsthumanity.security.http.resolvers;

import com.tj.cardsagainsthumanity.exceptions.UnresolvableException;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.security.auth.PlayerUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Component
public class SessionService implements PlayerResolver<HttpServletRequest> {

    @Override
    public Player resolveFrom(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            throwIfNoSession(session);
            SecurityContext securityContext = (SecurityContext) session.getValue(SPRING_SECURITY_CONTEXT_KEY);
            throwIfNoResult(securityContext);
            Authentication authentication = securityContext.getAuthentication();
            PlayerUserDetails player = (PlayerUserDetails) authentication.getPrincipal();

            return player.getPlayer();
        } catch (ClassCastException e) {
            throw new UnresolvableException("Unable to resolve player from session");
        }
    }

    private void throwIfNoSession(HttpSession session) {
        throwIfNull(session, "No session established");
    }

    private void throwIfNull(Object item, String msg) {
        if (item == null) {
            throw new UnresolvableException(( msg ));
        }
    }

    private void throwIfNoResult(Object result) {
        throwIfNull(result, "No player on session");
    }
}

