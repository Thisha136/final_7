package com.thisha_cool.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="conversations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Column(unique = true)
    private String shareId;

    @Builder.Default
    private Boolean isPublic = false;
}