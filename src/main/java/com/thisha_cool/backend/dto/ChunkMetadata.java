package com.thisha_cool.backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChunkMetadata {

    private String text;

    private Integer pageNumber;

    private Integer paragraphNumber;
}