package com.itu.evaluation.controllers.admin;


import com.itu.evaluation.dto.LoginResponse;
import com.itu.evaluation.services.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }



    @PostMapping("/admin/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        Model model,
                        HttpSession session) {
        try {
            LoginResponse response = authService.login(email, password);
            session.setAttribute("token", response.getData().getToken());
            session.setAttribute("user", response.getData().getUser());
            return "redirect:/admin/dashboard";
        } catch (Exception e) {
            model.addAttribute("message", "Identifiants incorrects !");
            return "redirect:/admin/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Supprime la session
        return "redirect:/admin/login";
    }
}