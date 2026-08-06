package com.thisha_cool.backend.service;

import com.thisha_cool.backend.model.Embedding;

public interface VectorStoreService {

    void storeEmbedding(Embedding embedding);

}