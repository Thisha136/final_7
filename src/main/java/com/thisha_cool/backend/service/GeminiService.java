package com.thisha_cool.backend.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.thisha_cool.backend.config.GeminiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {

    @Autowired
    private GeminiConfig geminiConfig;

    private static final String MODEL_NAME = "gemini-2.0-flash";

    public String askGemini(String prompt) {

        try {

            Client client = Client.builder()
                    .apiKey(geminiConfig.getApiKey())
                    .build();

            System.out.println("Using model: gemini-2.0-flash");

            GenerateContentResponse response =
                    client.models.generateContent(
                            "gemini-2.0-flash",
                            prompt,
                            null
                    );

            return response.text();

        } catch (Exception e) {
            throw new RuntimeException("Error calling Gemini API: " + e.getMessage(), e);
        }
    }
}