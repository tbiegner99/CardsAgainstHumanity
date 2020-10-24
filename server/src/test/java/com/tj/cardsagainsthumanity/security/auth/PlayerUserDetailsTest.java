package com.tj.cardsagainsthumanity.security.auth;

import com.tj.cardsagainsthumanity.models.gameplay.Credentials;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;

public class PlayerUserDetailsTest {

    private Player testPlayer;
    private PlayerUserDetails userDetails;

    @Before
    public void setUp() throws Exception {
        Credentials creds = new Credentials();
        creds.setPassword("testPass");

        testPlayer = new Player();
        testPlayer.setDeleted(true);
        testPlayer.setEmail("testEmail");
        testPlayer.setCredentials(creds);

        userDetails = new PlayerUserDetails(testPlayer);
    }

    @Test
    public void getAuthorities() {
        assertEquals(userDetails.getAuthorities(), Arrays.asList());
    }

    @Test
    public void getPassword() {
        assertEquals(userDetails.getPassword(), "testPass");
    }

    @Test
    public void getUsername() {
        assertEquals(userDetails.getUsername(), "testEmail");
    }

    @Test
    public void isAccountNonExpired() {
        assertFalse(userDetails.isAccountNonExpired());
    }

    @Test
    public void isAccountNonLocked() {
        assertFalse(userDetails.isAccountNonLocked());
    }

    @Test
    public void isCredentialsNonExpired() {
        assertFalse(userDetails.isCredentialsNonExpired());
    }

    @Test
    public void isEnabled() {
        assertFalse(userDetails.isEnabled());
    }
}