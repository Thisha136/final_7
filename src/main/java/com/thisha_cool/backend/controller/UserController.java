package com.thisha_cool.backend.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @PostMapping("/chat")
    public String chat() {
        return "Chat API Working";
    }
}