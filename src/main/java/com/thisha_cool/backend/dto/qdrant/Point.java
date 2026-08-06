package com.thisha_cool.backend.dto.qdrant;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Point {

    private int id;

    private List<Float> vector;

    private Payload payload;
}