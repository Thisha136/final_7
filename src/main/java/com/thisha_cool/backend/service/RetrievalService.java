package com.thisha_cool.backend.service;

import com.thisha_cool.backend.dto.qdrant.response.SearchPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class RetrievalService {

    @Autowired
    private EmbeddingService embeddingService;

    @Autowired
    private GroqService groqService;

    @Autowired
    private QdrantService qdrantService;

    // -----------------------------
    // Simple Chat (No Memory)
    // -----------------------------
    public String search(String question) {

        return search(question, "");
    }

    // -----------------------------
    // Chat With Conversation Memory
    // -----------------------------
    public String search(String question, String conversationHistory) {

        // Build retrieval query
        String retrievalQuery = question;

        if (conversationHistory != null &&
                !conversationHistory.isBlank()) {

            retrievalQuery =
                    conversationHistory
                            + "\nCurrent Question: "
                            + question;
        }

        System.out.println("========== RETRIEVAL QUERY ==========");
        System.out.println(retrievalQuery);

        // Generate embedding
        List<Float> embedding =
                embeddingService.generateEmbedding(retrievalQuery);

        // Search in Qdrant
        List<SearchPoint> searchResults =
                qdrantService.searchEmbedding(embedding);

        String latestUploadedAt = searchResults.stream()
                .map(point -> point.getPayload().getUploadedAt())
                .filter(Objects::nonNull)
                .max(String::compareTo)
                .orElse("");

        List<SearchPoint> latestResults =
                searchResults.stream()
                        .filter(point ->
                                point.getPayload()
                                        .getUploadedAt()
                                        .equals(latestUploadedAt))
                        .toList();

        // Build Context
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

            context.append("Content:\n")
                    .append(point.getPayload().getText())
                    .append("\n\n");
        }

        // Prompt
        String prompt =
                """
                You are an enterprise document assistant.

                The previous user questions are provided only to understand what
                the current question refers to.

                NEVER answer from previous questions.

                ALWAYS answer ONLY from the Retrieved Document Context.

                If the current question is a follow-up such as:

                - Explain it
                - Tell me more
                - What does that mean
                - Which section
                - Continue

                use the Previous User Questions only to resolve the reference.

                If the Retrieved Document Context does not contain the answer,
                reply exactly:

                I couldn't find this information in the uploaded documents.

                Previous User Questions:
                %s

                Retrieved Document Context:
                %s

                Current User Question:
                %s
                """
                        .formatted(
                                conversationHistory,
                                context.toString(),
                                question
                        );

        System.out.println("========== PROMPT ==========");
        System.out.println(prompt);

        String answer =
                groqService.askGroq(prompt);

        // Append Sources

        answer += "\n\nSources:\n";

        int count1 = Math.min(3, searchResults.size());

        for (int i = 0; i < count1; i++) {

            SearchPoint point = searchResults.get(i);

            answer += "\n" + (i + 1) + ".\n";
            answer += "File Name: "
                    + point.getPayload().getFileName()
                    + "\n";

            answer += "Page Number: "
                    + point.getPayload().getPageNumber()
                    + "\n";

            answer += "Paragraph: "
                    + point.getPayload().getParagraph()
                    + "\n";

            answer += "Confidence: "
                    + String.format("%.2f%%",
                    point.getScore() * 100)
                    + "\n";
        }

        return answer;
    }
}