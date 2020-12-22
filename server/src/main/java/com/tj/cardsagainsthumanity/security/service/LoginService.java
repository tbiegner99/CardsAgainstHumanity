package com.tj.cardsagainsthumanity.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class LoginService {
    private AuthenticationManager authenticationManager;

    @Autowired
    public LoginService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    SecurityContext getSecurityContext() {
        return SecurityContextHolder.getContext();
    }

    public SecurityContext login(String user, String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, password);
        Authentication auth = authenticationManager.authenticate(token);
        if (!auth.isAuthenticated()) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        SecurityContext context = getSecurityContext();
        context.setAuthentication(auth);
        return context;
    }
}
