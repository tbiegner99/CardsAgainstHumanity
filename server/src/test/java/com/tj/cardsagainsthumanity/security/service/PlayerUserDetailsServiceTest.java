package com.tj.cardsagainsthumanity.security.service;

import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.security.auth.PlayerUserDetails;
import com.tj.cardsagainsthumanity.services.gameplay.PlayerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlayerUserDetailsServiceTest {
    @Mock
    private PlayerService playerService;
    @Mock
    private Player player;

    private PlayerUserDetailsService service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(playerService.getPlayerByEmail(any())).thenReturn(player);
        service = new PlayerUserDetailsService(playerService);
    }

    @Test
    public void loadUserByUsername() {
        UserDetails result = service.loadUserByUsername("testEmail");
        UserDetails expectedResult = new PlayerUserDetails(player);
        verify(playerService, times(1)).getPlayerByEmail("testEmail");
        assertEquals(expectedResult, result);
    }
}