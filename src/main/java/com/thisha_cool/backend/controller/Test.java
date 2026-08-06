package com.thisha_cool.backend.controller;

import com.thisha_cool.backend.service.EmbeddingService;
import com.thisha_cool.backend.service.QdrantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.thisha_cool.backend.service.GroqService;
@RestController
public class Test {

    @Autowired
    private GroqService groqService;

    @GetMapping("/groq")
    public String test() {

        return groqService.askGroq("Say Hello");

    }
    @GetMapping("/test")
    public String test1() {
        return "Working";
    }
}