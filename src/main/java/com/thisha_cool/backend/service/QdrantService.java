package com.thisha_cool.backend.service;

import com.thisha_cool.backend.dto.qdrant.response.SearchResponse;
import com.thisha_cool.backend.dto.qdrant.response.SearchPoint;
import com.thisha_cool.backend.config.QdrantConfig;
import com.thisha_cool.backend.dto.qdrant.Payload;
import com.thisha_cool.backend.dto.qdrant.Point;
import com.thisha_cool.backend.dto.qdrant.PointRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class QdrantService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private QdrantConfig qdrantConfig;

    @Value("${qdrant.url}")
    private String qdrantUrl;

    @Value("${qdrant.collection}")
    private String collectionName;

    // ✅ CREATE COLLECTION
    public String createCollection() {

        String url = qdrantUrl + "/collections/" + collectionName;

        String body = """
        {
          "vectors": {
            "size": 3072,
            "distance": "Cosine"
          }
        }
        """;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        return restTemplate.exchange(
                url,
                HttpMethod.PUT,
                entity,
                String.class
        ).getBody();
    }

    // ✅ SEARCH
    public List<SearchPoint> searchEmbedding(List<Float> embedding) {

        String url = qdrantUrl +
                "/collections/" +
                collectionName +
                "/points/query";

        if (embedding == null || embedding.isEmpty()) {
            throw new RuntimeException("Embedding is empty!");
        }

        Map<String, Object> body = new HashMap<>();
        body.put("query", embedding);
        body.put("limit", 5);
        body.put("with_payload", true);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(body, headers);

        ResponseEntity<SearchResponse> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        entity,
                        SearchResponse.class
                );

        return response.getBody().getResult().getPoints();
    }

    // ✅ STORE EMBEDDING (FIXED)
    public String storeEmbedding(
            int id,
            String text,
            String fileName,
            Integer pageNumber,
            String paragraph,
            String uploadedAt,
            List<Float> embedding) {

        String url = qdrantUrl +
                "/collections/" +
                collectionName +
                "/points";

        // 🔴 VALIDATION (VERY IMPORTANT)
        if (embedding == null || embedding.isEmpty()) {
            throw new RuntimeException("Embedding is null or empty");
        }

        System.out.println("Embedding size: " + embedding.size());

        // ⚠️ Must match collection size (3072)
        if (embedding.size() != 3072) {
            throw new RuntimeException(
                    "Embedding size mismatch. Expected 3072, got " + embedding.size()
            );
        }

        // ✅ SAFE PAYLOAD
        Payload payload = Payload.builder()
                .text(text)
                .fileName(fileName)
                .pageNumber(pageNumber)
                .paragraph(paragraph)
                .uploadedAt(uploadedAt)
                .build();

        Point point = Point.builder()
                .id(id)
                .vector(embedding)
                .payload(payload)
                .build();

        PointRequest request = PointRequest.builder()
                .points(List.of(point))
                .build();

        // 🔥 DEBUG JSON (CRITICAL)
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(request);

            System.out.println("QDRANT REQUEST JSON:");
            System.out.println(json);

        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PointRequest> entity =
                new HttpEntity<>(request, headers);

        try {
            ResponseEntity<String> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.PUT,
                            entity,
                            String.class
                    );

            return response.getBody();

        } catch (Exception e) {
            e.printStackTrace(); // 🔥 THIS SHOWS REAL ERROR
            throw new RuntimeException("Qdrant API failed: " + e.getMessage());
        }
    }
}