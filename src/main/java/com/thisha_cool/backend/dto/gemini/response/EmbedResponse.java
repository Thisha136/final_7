package com.thisha_cool.backend.dto.gemini.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmbedResponse {

    private Embedding embedding;
}
