package com.thisha_cool.backend.service;

import com.thisha_cool.backend.model.Embedding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentProcessingService {

    @Autowired
    private EmbeddingService embeddingService;

    @Autowired
    private VectorStoreService vectorStoreService;

    public void processChunks(List<String> chunks) {

        long id = 1;

        for (String chunk : chunks) {

            Embedding embedding = Embedding.builder()
                    .chunkId(id++)
                    .text(chunk)
                    .vector(embeddingService.generateEmbedding(chunk))
                    .build();

            vectorStoreService.storeEmbedding(embedding);

        }
    }
}