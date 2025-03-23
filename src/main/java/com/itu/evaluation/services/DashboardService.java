package com.itu.evaluation.services;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class DashboardService {

private final RestTemplate restTemplate;
public DashboardService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
}

    public Map<String, Object> getKPIs(String token) {
        String url = "http://localhost/api/dashboards";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        return response.getBody();
    }

}
