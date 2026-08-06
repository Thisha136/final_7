package com.thisha_cool.backend.dto.qdrant;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PointRequest {

    private List<Point> points;
}