package com.thisha_cool.backend.dto.qdrant.response;

import lombok.Data;

@Data
public class SearchPoint {

    private Integer id;

    private Double score;

    private Payload payload;
}