package com.thisha_cool.backend.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Embedding {

    private Long chunkId;

    private String text;

    private List<Float> vector;

}