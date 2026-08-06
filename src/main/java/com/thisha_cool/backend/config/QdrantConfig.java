package com.thisha_cool.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QdrantConfig {

    @Value("${qdrant.url}")
    private String url;

    @Value("${qdrant.collection}")
    private String collection;

    public String getUrl() {
        return url;
    }

    public String getCollection() {
        return collection;
    }
}