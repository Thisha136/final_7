package com.thisha_cool.backend.dto.qdrant;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Payload {

    private String text;

    private String fileName;

    private Integer pageNumber;

    private String paragraph;
}