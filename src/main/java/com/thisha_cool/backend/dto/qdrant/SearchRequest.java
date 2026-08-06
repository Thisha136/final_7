package com.thisha_cool.backend.dto.qdrant;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchRequest {

    private List<Float> vector;

    private int limit;
}