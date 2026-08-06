package com.thisha_cool.backend.repository;

import com.thisha_cool.backend.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}