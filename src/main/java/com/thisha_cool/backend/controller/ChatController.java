package com.thisha_cool.backend.controller;

import com.thisha_cool.backend.entity.Conversation;
import com.thisha_cool.backend.service.ChatMessageService;
import com.thisha_cool.backend.service.ConversationService;
import com.thisha_cool.backend.entity.Conversation;
import com.thisha_cool.backend.service.RetrievalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private RetrievalService retrievalService;
    @Autowired
    private ConversationService conversationService;
    @Autowired
    private ChatMessageService chatMessageService;

    // Simple chat (does not save history)
    @GetMapping("/simple")
    public String simpleChat(@RequestParam String question) {

        return retrievalService.search(question);
    }


    @PostMapping("/share/{shareId}")
    public String continueSharedConversation(
            @PathVariable String shareId,
            @RequestParam String question) {

        // Find conversation using shareId
        Conversation conversation =
                conversationService.getConversationByShareId(shareId);

        // Build previous conversation history
        String history =
                chatMessageService.buildConversationContext(conversation.getId());

        // Ask RAG
        String answer =
                retrievalService.search(question, history);

        // Save new message into the same conversation
        chatMessageService.saveMessage(
                conversation.getId(),
                question,
                answer
        );

        return answer;
    }
    // Conversation chat (saves history)
    @PostMapping
    public String ask(@RequestParam Long conversationId,
                      @RequestParam String question) {

        String history = chatMessageService.buildConversationContext(conversationId);

        String answer = retrievalService.search(question, history);

        chatMessageService.saveMessage(
                conversationId,
                question,
                answer
        );

        return answer;
    }
}