package com.thisha_cool.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "document_chunks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentChunk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer chunkNumber;

    @Column(columnDefinition = "TEXT")
    private String chunkText;

    // NEW
    private Integer pageNumber;

    // NEW
    private String paragraph;

    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;
}