package com.tj.cardsagainsthumanity.security.auth;

import com.tj.cardsagainsthumanity.models.gameplay.Player;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class PlayerUserDetails implements UserDetails {

    private final Player player;

    public PlayerUserDetails(Player player) {
        this.player = player;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList();
    }

    @Override
    public String getPassword() {
        return  player.getCredentials().getPassword();
    }

    @Override
    public String getUsername() {
        return player.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !player.isDeleted();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !player.isDeleted();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !player.isDeleted();
    }

    @Override
    public boolean isEnabled() {
        return !player.isDeleted();
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean equals(Object o) {
        PlayerUserDetails that = (PlayerUserDetails) o;
        return Objects.equals(player, that.player);
    }


}
