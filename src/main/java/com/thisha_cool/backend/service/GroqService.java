package com.thisha_cool.backend.service;

import com.thisha_cool.backend.config.GroqConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class GroqService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GroqConfig groqConfig;

    private static final String URL =
            "https://api.groq.com/openai/v1/chat/completions";

    private static final String MODEL =
            "llama-3.3-70b-versatile";

    public String askGroq(String prompt) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(groqConfig.getApiKey());

        Map<String, Object> requestBody = Map.of(
                "model", MODEL,
                "messages", List.of(
                        Map.of(
                                "role", "user",
                                "content", prompt
                        )
                ),
                "temperature", 0.2
        );

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(requestBody, headers);

        try {

            Map<?, ?> response =
                    restTemplate.postForObject(URL, entity, Map.class);

            return extractText(response);

        } catch (Exception e) {
            throw new RuntimeException("Groq API Error : "
                    + e.getMessage(), e);
        }

    }

    private String extractText(Map<?, ?> response) {

        if (response == null)
            return "No response";

        List<?> choices = (List<?>) response.get("choices");

        if (choices == null || choices.isEmpty())
            return "No choices returned";

        Map<?, ?> choice = (Map<?, ?>) choices.get(0);

        Map<?, ?> message = (Map<?, ?>) choice.get("message");

        return message.get("content").toString();

    }

}
