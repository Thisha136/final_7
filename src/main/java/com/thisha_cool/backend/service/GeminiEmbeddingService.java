package com.thisha_cool.backend.service;

import com.thisha_cool.backend.dto.gemini.Content;
import com.thisha_cool.backend.dto.gemini.EmbedRequest;
import com.thisha_cool.backend.dto.gemini.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.thisha_cool.backend.config.GeminiConfig;
import java.util.List;
import com.thisha_cool.backend.dto.gemini.response.EmbedResponse;
@Service
public class GeminiEmbeddingService implements EmbeddingService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GeminiConfig geminiConfig;

    @Override
    public List<Float> generateEmbedding(String text) {


        System.out.println("===== GEMINI START =====");

        String url =
                "https://generativelanguage.googleapis.com/v1beta/models/gemini-embedding-001:embedContent?key="
                        + geminiConfig.getApiKey();

        EmbedRequest request = EmbedRequest.builder()
                .content(
                        Content.builder()
                                .parts(
                                        List.of(
                                                Part.builder()
                                                        .text(text)
                                                        .build()
                                        )
                                )
                                .build()
                )
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<EmbedRequest> entity =
                new HttpEntity<>(request, headers);

        EmbedResponse response =
                restTemplate.postForObject(
                        url,
                        entity,
                        EmbedResponse.class
                );

        System.out.println("Embedding Dimension = "
                + response.getEmbedding().getValues().size());

        return response.getEmbedding().getValues();

    }}