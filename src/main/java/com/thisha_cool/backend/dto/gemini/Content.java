package com.thisha_cool.backend.dto.gemini;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Content {

    private List<Part> parts;

}