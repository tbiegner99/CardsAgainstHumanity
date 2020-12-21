package com.tj.cardsagainsthumanity.server.websocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        HttpSession session = ((ServletServerHttpRequest) request).getServletRequest().getSession(true);
        Cookie currentGame = WebUtils.getCookie(((ServletServerHttpRequest) request).getServletRequest(), "currentGame");
        attributes.put("session", session);
        if (currentGame != null) {
            attributes.put("currentGame", currentGame.getValue());
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }
}
