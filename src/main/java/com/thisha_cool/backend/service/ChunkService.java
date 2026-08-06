package com.thisha_cool.backend.service;

import com.thisha_cool.backend.dto.ChunkMetadata;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChunkService {

    private static final int CHUNK_SIZE = 500;

    public List<ChunkMetadata> createChunks(String text) {

        List<ChunkMetadata> chunks = new ArrayList<>();

        if (text == null || text.isBlank()) {
            return chunks;
        }

        int pageNumber = 1;
        int paragraphNumber = 1;

        for (int i = 0; i < text.length(); i += CHUNK_SIZE) {

            int end = Math.min(i + CHUNK_SIZE, text.length());

            String chunkText = text.substring(i, end);

            chunks.add(
                    ChunkMetadata.builder()
                            .text(chunkText)
                            .pageNumber(pageNumber)          // Temporary
                            .paragraphNumber(paragraphNumber++) // Temporary
                            .build()
            );
        }

        return chunks;
    }
}