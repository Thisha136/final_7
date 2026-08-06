package com.thisha_cool.backend.controller;

import com.thisha_cool.backend.service.RetrievalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private RetrievalService retrievalService;

    @GetMapping
    public String ask(@RequestParam String question) {

        return retrievalService.search(question);
    }
}