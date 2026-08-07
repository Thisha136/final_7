package com.thisha_cool.backend.service;

import com.thisha_cool.backend.dto.ChunkMetadata;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChunkService {

    private static final int CHUNK_SIZE = 500;
    private static final int OVERLAP = 150;

    public List<ChunkMetadata> createChunks(String text) {

        List<ChunkMetadata> chunks = new ArrayList<>();

        if (text == null || text.isBlank()) {
            return chunks;
        }

        int paragraphNumber = 1;
        int start = 0;

        while (start < text.length()) {

            int end = Math.min(start + CHUNK_SIZE, text.length());

            // Try to end at a newline
            if (end < text.length()) {

                int newline = text.lastIndexOf('\n', end);

                if (newline > start + 200) {
                    end = newline;
                }
            }

            String chunk = text.substring(start, end).trim();

            if (!chunk.isEmpty()) {

                chunks.add(
                        ChunkMetadata.builder()
                                .text(chunk)
                                .paragraphNumber(paragraphNumber++)
                                .build()
                );
            }

            // IMPORTANT:
            // If this is the final chunk, stop.
            if (end >= text.length()) {
                break;
            }

            // Move forward while maintaining overlap
            start = end - OVERLAP;

            // Safety check to guarantee progress
            if (start <= 0) {
                start = end;
            }
        }

        return chunks;
    }
}