package com.thisha_cool.backend.dto.qdrant.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payload {

    private String text;
    private String fileName;
    private Integer pageNumber;
    private String paragraph;
    private String uploadedAt;
}