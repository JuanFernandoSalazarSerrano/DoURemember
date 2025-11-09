package com.springboot.backend.salazar.usersbackend.users_backend.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.Collections;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.backend.salazar.usersbackend.users_backend.entities.GroundTruthResponse;
import com.springboot.backend.salazar.usersbackend.users_backend.repositories.GroundTruthRepository;

@Service
public class GroundTruthServiceImpl implements GroundTruthService {

    private final GroundTruthRepository repository;
    private final RestTemplate restTemplate = new RestTemplate();

    public GroundTruthServiceImpl(GroundTruthRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void generateGroundTruthTestSendAnswer(String userAnswer) {

    String url = "http://localhost:8000/api/ai/v1";

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    ObjectMapper mapper = new ObjectMapper();
    try {
        String jsonBody = mapper.writeValueAsString(Collections.singletonMap("userAnswer", userAnswer));
        System.out.println("Outgoing JSON -> " + jsonBody);

        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        System.out.println("Response from Django: " + response.getBody());

    } catch (RestClientException | com.fasterxml.jackson.core.JsonProcessingException e) {
        e.printStackTrace();
    }
}

    @Override
    public GroundTruthResponse saveGroundTruthResponse(GroundTruthResponse groundTruthResponse) {
        System.out.println("HOLA AMIGOS "+ groundTruthResponse);
        System.out.println("HOLA AMIGO TEST");
        return repository.save(groundTruthResponse);
    }
}
