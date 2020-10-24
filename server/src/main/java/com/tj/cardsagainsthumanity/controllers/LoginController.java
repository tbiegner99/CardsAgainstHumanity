package com.tj.cardsagainsthumanity.controllers;

import com.tj.cardsagainsthumanity.serializer.requestModel.user.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Controller
public class LoginController {

    public AuthenticationManager authenticationManager;

    public LoginController(@Autowired AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity<?> login(HttpServletRequest request, @RequestBody LoginRequest body) {
        UsernamePasswordAuthenticationToken authReq
                = new UsernamePasswordAuthenticationToken(body.getUsername(), body.getPassword());
        Authentication auth = authenticationManager.authenticate(authReq);

        SecurityContext sc = getSecurityContext();
        sc.setAuthentication(auth);
        HttpSession session = request.getSession(true);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);

        return ResponseEntity.noContent()
                .build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) throws ServletException {
       HttpSession session = request.getSession(false);
       if(session!=null) {
           session.invalidate();
       }

        return ResponseEntity.noContent()
                .build();
    }

    public SecurityContext getSecurityContext() {
        return SecurityContextHolder.getContext();
    }
}
