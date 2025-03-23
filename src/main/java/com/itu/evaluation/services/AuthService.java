package com.itu.evaluation.services;

import com.itu.evaluation.dto.LoginResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    private final RestTemplate restTemplate;

    public AuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public LoginResponse login(String email, String password) {
        String url = "http://localhost/api/login-api";
        Map<String, String> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<LoginResponse> response = restTemplate.exchange(
                url, HttpMethod.POST, request, LoginResponse.class
        );

        return response.getBody();
    }
}