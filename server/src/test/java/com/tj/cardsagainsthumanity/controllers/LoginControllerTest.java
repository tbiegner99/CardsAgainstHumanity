package com.tj.cardsagainsthumanity.controllers;


import com.tj.cardsagainsthumanity.serializer.requestModel.user.LoginRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

public class LoginControllerTest {
    private final String testUser = "testUser";
    private final String testPassword = "testPassword";
    @Mock
    private HttpServletRequest mockRequest;
    @Mock
    private HttpSession mockSession;
    @Mock
    private SecurityContext mockContext;

    private LoginRequest loginRequest;
    @Mock
    private AuthenticationManager authManager;
    @Mock
    private Authentication auth;
    private LoginController controller;

    @Before
    public void setUp() throws Exception {
        loginRequest = new LoginRequest();
        loginRequest.setUsername(testUser);
        loginRequest.setPassword(testPassword);


        MockitoAnnotations.initMocks(this);

        controller = spy(new LoginController(authManager));
        when(authManager.authenticate(any())).thenReturn(auth);
        when(mockRequest.getSession(true)).thenReturn(mockSession);
        when(controller.getSecurityContext()).thenReturn(mockContext);


    }

    @Test
    public void login() {
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(testUser, testPassword);
        ResponseEntity<?> responseEntity = controller.login(mockRequest, loginRequest);
        //it authenticates correctly
        verify(authManager, times(1)).authenticate(authReq);
        //it sets authentication in context
        verify(mockContext, times(1)).setAuthentication(auth);
        //it saves context in session
        verify(mockSession, times(1)).setAttribute(SPRING_SECURITY_CONTEXT_KEY, mockContext);
        //it returns with 204
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NO_CONTENT);
    }

}