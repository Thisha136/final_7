package com.thisha_cool.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.thisha_cool.backend.dto.qdrant.response.SearchPoint;
import java.util.List;


@Service
public class RetrievalService {

    @Autowired
    private EmbeddingService embeddingService;
    @Autowired
    private GroqService groqService;
    @Autowired
    private QdrantService qdrantService;


    public String search(String question) {

        // Generate embedding
        List<Float> embedding =
                embeddingService.generateEmbedding(question);

        // Search in Qdrant
        List<SearchPoint> searchResults =
                qdrantService.searchEmbedding(embedding);

        // Build context from retrieved chunks
        StringBuilder context = new StringBuilder();

        for (SearchPoint point : searchResults) {

            context.append("File: ")
                    .append(point.getPayload().getFileName())
                    .append("\n");

            context.append("Page: ")
                    .append(point.getPayload().getPageNumber())
                    .append("\n");

            context.append("Content:\n")
                    .append(point.getPayload().getText())
                    .append("\n\n");
        }

// Prompt Groq
        String prompt =
                """
                        You are an enterprise document assistant.
                        
                        Answer ONLY using the information below.
                        
                        Context:
                        %s
                        
                        Question:
                        %s
                        
                        If the answer is not available, reply exactly:
                        "I couldn't find this information in the uploaded documents."
                        
                        At the end of your answer, include:
                        
                        Sources:
                        - File Name
                        - Page Number
                        """
                        .formatted(context.toString(), question);

        return groqService.askGroq(prompt);
    }}