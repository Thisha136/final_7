package com.thisha_cool.backend.service;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.thisha_cool.backend.dto.qdrant.response.SearchPoint;
import java.util.*;


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
        String latestUploadedAt = searchResults.stream()
                .map(point -> point.getPayload().getUploadedAt())
                .filter(Objects::nonNull)
                .max(String::compareTo)
                .orElse("");
        List<SearchPoint> latestResults = searchResults.stream()
                .filter(point ->
                        point.getPayload().getUploadedAt().equals(latestUploadedAt))
                .toList();


        // Build context from retrieved chunks
        StringBuilder context = new StringBuilder();

        int count = Math.min(3, latestResults.size());

        for (int i = 0; i < count; i++) {

            SearchPoint point = latestResults.get(i);

            context.append("File: ")
                    .append(point.getPayload().getFileName())
                    .append("\n");

            context.append("Page: ")
                    .append(point.getPayload().getPageNumber())
                    .append("\n");
            context.append("Paragraph: ")
                    .append(point.getPayload().getParagraph())
                    .append("\n");
            context.append("Confidence: ")
                    .append(String.format("%.2f%%", point.getScore() * 100))
                    .append("\n");
            context.append("Content:\n")
                    .append(point.getPayload().getText())
                    .append("\n\n");
        }

// Prompt Groq
        String prompt =
                """
                You are an enterprise document assistant.
        
                Answer ONLY using the information provided in the Context.
        
                If the answer is not explicitly available in the Context, reply exactly:
                "I couldn't find this information in the uploaded documents."
        
                Do not use outside knowledge.
                Do not guess or invent information.
        
                Context:
                %s
        
                Question:
                %s
                """
                        .formatted(context.toString(), question);
                        

        String answer = groqService.askGroq(prompt);

// Append citations from backend
        answer += "\n\nSources:\n";

        int count1 = Math.min(3, searchResults.size());

        for (int i = 0; i < count1; i++) {

            SearchPoint point = searchResults.get(i);

            answer += "\n" + (i + 1) + ".\n";
            answer += "File Name: " + point.getPayload().getFileName() + "\n";
            answer += "Page Number: " + point.getPayload().getPageNumber() + "\n";
            answer += "Paragraph: " + point.getPayload().getParagraph() + "\n";
            answer += "Confidence: "
                    + String.format("%.2f%%", point.getScore() * 100)
                    + "\n";
        }

        return answer;
    }

}