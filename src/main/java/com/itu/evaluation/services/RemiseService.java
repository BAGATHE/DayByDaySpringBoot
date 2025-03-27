package com.itu.evaluation.services;

import com.itu.evaluation.constante.UrlApi;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class RemiseService {
private final RestTemplate restTemplate;

public RemiseService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
}


    public Map<String, Object> getRemise(String token){
        String url = UrlApi.REMISE;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        return response.getBody();
    }


    public Map<String, Object> UPDATEREMISE(String token, double remise) {
        String url = UrlApi.UPDATEREMISE;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("remise", remise);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

        return response.getBody();
    }

    public Map<String, Object> importclient(String token, MultipartFile file) throws IOException {
        String url = UrlApi.IMPORT_ENTITY; // Remplacez par votre URL d'API

        // Vérifier si le fichier est vide
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Le fichier est vide.");
        }

        // Lire le contenu du fichier JSON
        String jsonContent = new String(file.getBytes(), StandardCharsets.UTF_8);

        // Configurer les en-têtes
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Construire la requête
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonContent, headers);

        // Envoyer la requête avec RestTemplate
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);

        return response.getBody();
    }

    public Map<String,Object> CLIENTIMPORT(String token, @NotNull MultipartFile file) throws IOException {
        String url = UrlApi.IMPORT_ENTITY; // Remplacez par votre URL d'API

        // Vérifier si le fichier est vide
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Le fichier est vide.");
        }

        // Lire le contenu du fichier JSON
        String jsonContent = new String(file.getBytes(), StandardCharsets.UTF_8);

        // Configurer les en-têtes
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Construire la requête
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonContent, headers);

        // Envoyer la requête avec RestTemplate
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);

        return response.getBody();
    }
}
