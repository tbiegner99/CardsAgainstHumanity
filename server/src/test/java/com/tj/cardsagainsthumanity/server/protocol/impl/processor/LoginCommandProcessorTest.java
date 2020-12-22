package com.tj.cardsagainsthumanity.server.protocol.impl.processor;

import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.security.auth.PlayerUserDetails;
import com.tj.cardsagainsthumanity.security.service.LoginService;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.LoginCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.LoginInfo;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.EmptyResponse;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.body.LoginResponseBody;
import com.tj.cardsagainsthumanity.server.protocol.message.CommandContext;
import com.tj.cardsagainsthumanity.server.protocol.message.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoginCommandProcessorTest {
    LoginCommandProcessor processor;
    Response<LoginResponseBody> result;
    @Mock
    LoginService loginService;
    @Mock
    CommandContext context;
    @Mock
    LoginCommand message;
    @Mock
    SecurityContext mockSecurityContext;
    @Mock
    Authentication mockAuth;
    @Mock
    PlayerUserDetails playerDetails;
    @Mock
    Player currentPlayer;

    private Integer playerId = 7;
    private String user = "user";
    private String pass = "password";
    private LoginInfo mockRequest = new LoginInfo(user, pass);

    @Before
    public void beforeEach() {
        processor = new LoginCommandProcessor(loginService);
        when(loginService.login(user, pass)).thenReturn(mockSecurityContext);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuth);
        when(mockAuth.getPrincipal()).thenReturn(playerDetails);
        when(playerDetails.getPlayer()).thenReturn(currentPlayer);
        when(currentPlayer.getId()).thenReturn(playerId);
        when(message.getArguments()).thenReturn(mockRequest);
        when(message.getMessageId()).thenReturn("someId");
    }


    @Test
    public void itPerformsLogin() {
        result = processor.processMessage(message, context);
        verify(loginService, times(1)).login(user, pass);
    }

    @Test
    public void itAddsPlayerToConnectionContext() {
        result = processor.processMessage(message, context);
        verify(context, times(1)).login(currentPlayer);
    }

    @Test
    public void itReturnsExpectedResponse() {
        result = processor.processMessage(message, context);
        LoginResponseBody body = result.getBody();
        assertEquals(result.getStatus(), 200);
        assertEquals(result.getStatusMessage(), "OK");
        assertEquals(body.getPlayerId(), playerId);
        assertNotNull(body.getToken());
    }

    @Test
    public void whenLoginFails_itDoesntAddPlayerToContext() {
        when(loginService.login(any(), any())).thenThrow(new IllegalArgumentException());
        result = processor.processMessage(message, context);
        verify(context, times(0)).login(currentPlayer);
    }

    @Test
    public void whenLoginFails_itReturnsForbiddenResponse() {
        when(loginService.login(any(), any())).thenThrow(new IllegalArgumentException());
        result = processor.processMessage(message, context);
        assertEquals(result, EmptyResponse.FORBIDDEN.forMessage("someId"));

    }
}