package com.thisha_cool.backend.dto.qdrant.response;

import lombok.Data;
import java.util.List;

@Data
public class SearchResponse {

    private Result result;

    @Data
    public static class Result {
        private List<SearchPoint> points;
    }
}
