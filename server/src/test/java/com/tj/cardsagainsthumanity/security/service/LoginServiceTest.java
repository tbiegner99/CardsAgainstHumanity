package com.tj.cardsagainsthumanity.security.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {
    private final String user = "user";
    private final String pass = "pass";
    private LoginService service;
    @Mock
    private AuthenticationManager authManager;
    @Mock
    private Authentication auth;
    @Mock
    private SecurityContext context;


    @Before
    public void setUp() throws Exception {
        service = spy(new LoginService(authManager));
        doReturn(auth).when(authManager).authenticate(any());
        when(auth.isAuthenticated()).thenReturn(true);
    }

    @Test
    public void getSecurityContext() {
        service.getSecurityContext();
    }

    @Test
    public void login() {
        doReturn(context).when(service).getSecurityContext();
        SecurityContext result = service.login(user, pass);
        assertSame(result, context);
        verify(context, times(1)).setAuthentication(auth);
        verify(authManager, times(1)).authenticate(any());
    }

    @Test
    public void login_whenNotAuthenticated_throwsError() {
        doReturn(context).when(service).getSecurityContext();
        when(auth.isAuthenticated()).thenReturn(false);
        try {
            service.login(user, pass);
            fail("Expected an error");
        } catch (IllegalArgumentException e) {
            return;
        }
    }


}