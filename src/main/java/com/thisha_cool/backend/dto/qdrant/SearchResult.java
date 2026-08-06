package com.thisha_cool.backend.dto.qdrant;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchResult {

    private String chunkText;

    private String fileName;

    private Integer pageNumber;

    private String paragraph;
}