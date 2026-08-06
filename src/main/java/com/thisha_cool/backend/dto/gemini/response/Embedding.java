package com.thisha_cool.backend.dto.gemini.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Embedding {

    private List<Float> values;
}