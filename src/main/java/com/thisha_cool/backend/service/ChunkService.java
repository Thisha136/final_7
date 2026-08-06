
package com.thisha_cool.backend.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChunkService {

    private static final int CHUNK_SIZE = 500;

    public List<String> createChunks(String text) {

        List<String> chunks = new ArrayList<>();

        if (text == null || text.isBlank()) {
            return chunks;
        }

        for (int i = 0; i < text.length(); i += CHUNK_SIZE) {

            int end = Math.min(i + CHUNK_SIZE, text.length());

            chunks.add(text.substring(i, end));
        }

        return chunks;
    }
}