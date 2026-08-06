package com.thisha_cool.backend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageResponse {

    private Long id;

    private String question;

    private String answer;

    private LocalDateTime askedAt;
}