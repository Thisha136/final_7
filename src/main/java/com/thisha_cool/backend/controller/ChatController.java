package com.thisha_cool.backend.controller;

import com.thisha_cool.backend.service.ChatMessageService;
import com.thisha_cool.backend.service.RetrievalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private RetrievalService retrievalService;

    @Autowired
    private ChatMessageService chatMessageService;

    // Simple chat (does not save history)
    @GetMapping("/simple")
    public String simpleChat(@RequestParam String question) {

        return retrievalService.search(question);
    }

    // Conversation chat (saves history)
    @GetMapping
    public String ask(@RequestParam Long conversationId,
                      @RequestParam String question) {

        String history = chatMessageService.buildConversationContext(conversationId);

        System.out.println("===== CONVERSATION HISTORY =====");
        System.out.println(history);
        System.out.println("===============================");
        String answer =
                retrievalService.search(question, history);

        chatMessageService.saveMessage(
                conversationId,
                question,
                answer
        );

        return answer;
    }
}