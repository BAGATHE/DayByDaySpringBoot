package com.itu.evaluation.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SecurityHandler {
    @GetMapping("/access-denied")
    public String accessDenied(){
        return "accessDenied";
    }
    @GetMapping("/")
    public String home() {
        return "redirect:/index.html";
    }
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @GetMapping("/admin/login")
    public String login() {
        return "admin/login";
    }
}