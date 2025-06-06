package com.itu.evaluation.services;

import com.itu.evaluation.constante.UrlApi;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardService {

private final RestTemplate restTemplate;
public DashboardService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
}

    public Map<String, Object> getKPIs(String token, String year) {
        String url = UrlApi.DASHBOARD + "?year=" + year;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> entity = new HttpEntity<>(headers); // Pas besoin de body

        // Envoyer la requête GET avec paramètres d'URL
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        return response.getBody();
    }





}
