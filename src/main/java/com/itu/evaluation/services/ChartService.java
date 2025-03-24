package com.itu.evaluation.services;

import com.itu.evaluation.constante.UrlApi;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ChartService {
    private final RestTemplate restTemplate;
    public ChartService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public Map<String, Object> getOffers(String token){
        String url = UrlApi.OFFERS;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        return response.getBody();
    }





}
