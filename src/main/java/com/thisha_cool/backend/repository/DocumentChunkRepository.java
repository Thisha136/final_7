package com.thisha_cool.backend.repository;

import com.thisha_cool.backend.entity.DocumentChunk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentChunkRepository extends JpaRepository<DocumentChunk, Long> {
}