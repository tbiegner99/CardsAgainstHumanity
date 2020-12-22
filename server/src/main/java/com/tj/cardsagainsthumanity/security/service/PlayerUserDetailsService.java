package com.tj.cardsagainsthumanity.security.service;

import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.security.auth.PlayerUserDetails;
import com.tj.cardsagainsthumanity.services.gameplay.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class PlayerUserDetailsService implements UserDetailsService {
    private PlayerService playerService;

    public PlayerUserDetailsService(@Autowired PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Player player = playerService.getPlayerByEmail(username);
        if(player==null) {
            throw new UsernameNotFoundException("No such user: "+username);
        }
        return new PlayerUserDetails(player);
    }
}
