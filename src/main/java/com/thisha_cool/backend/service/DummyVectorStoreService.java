package com.thisha_cool.backend.service;

import com.thisha_cool.backend.model.Embedding;
import org.springframework.stereotype.Service;

@Service
public class DummyVectorStoreService implements VectorStoreService {

    @Override
    public void storeEmbedding(Embedding embedding) {

        System.out.println("====================================");
        System.out.println("Chunk ID : " + embedding.getChunkId());
        System.out.println("Vector Size : " + embedding.getVector().size());
        System.out.println("====================================");

    }
}