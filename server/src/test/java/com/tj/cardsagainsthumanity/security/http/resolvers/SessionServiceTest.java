package com.tj.cardsagainsthumanity.security.http.resolvers;

import com.tj.cardsagainsthumanity.exceptions.UnresolvableException;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.security.auth.PlayerUserDetails;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;
import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@RunWith(MockitoJUnitRunner.class)
public class SessionServiceTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private Player player;
    @Mock
    private SecurityContext context;

    @Mock
    private Authentication auth;

    private PlayerUserDetails details;

    private SessionService resolver;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        resolver = new SessionService();
        details = new PlayerUserDetails(player);
        when(request.getSession()).thenReturn(session);
        when(session.getValue(SPRING_SECURITY_CONTEXT_KEY)).thenReturn(context);
        when(context.getAuthentication()).thenReturn(auth);
        when(auth.getPrincipal()).thenReturn(details);

    }

    @Test
    public void resolveFrom() {
        Player result = resolver.resolveFrom(request);
        assertSame(result, player);
    }

    @Test
    public void resolveFrom_whenNoSession() {
        when(session.getValue(SPRING_SECURITY_CONTEXT_KEY)).thenReturn(null);
        try {
            resolver.resolveFrom(request);
        } catch (UnresolvableException e) {
            return;
        }
        throw new RuntimeException();
    }

    @Test
    public void resolveFrom_whenObjectNotInSession() {
        when(session.getValue(SPRING_SECURITY_CONTEXT_KEY)).thenReturn(null);
        try {
            resolver.resolveFrom(request);
        } catch (UnresolvableException e) {
            return;
        }
        throw new RuntimeException();
    }

    @Test
    public void resolveFrom_whenObjectNotPlayer() {
        when(session.getValue(SPRING_SECURITY_CONTEXT_KEY)).thenReturn(new Object());
        try {
            resolver.resolveFrom(request);
        } catch (UnresolvableException e) {
            return;
        }
        throw new RuntimeException();
    }
}