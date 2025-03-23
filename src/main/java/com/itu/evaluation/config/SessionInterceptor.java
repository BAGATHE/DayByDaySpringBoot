package com.itu.evaluation.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HttpSession session = request.getSession(false); // Récupère la session sans la créer
        if (session != null) {
            logger.info("Session ID: {}", session.getId());
            logger.info("Token: {}", session.getAttribute("token"));
            logger.info("User: {}", session.getAttribute("user"));
        } else {
            logger.info("Aucune session active.");
        }
        return true;
    }
}