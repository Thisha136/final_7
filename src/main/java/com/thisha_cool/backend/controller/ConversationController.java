package com.thisha_cool.backend.controller;

import com.thisha_cool.backend.entity.Conversation;
import com.thisha_cool.backend.service.ConversationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat/conversations")
public class ConversationController {

    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    // Create Conversation
    @PostMapping
    public Conversation createConversation(
            @RequestParam String title) {

        return conversationService.createConversation(title);
    }

    // Get All Conversations
    @GetMapping
    public List<Conversation> getAllConversations() {

        return conversationService.getAllConversations();
    }
    // Search Conversations
    @GetMapping("/search")
    public List<Conversation> searchConversationHistory(
            @RequestParam String keyword) {

        return conversationService.searchConversationHistory(keyword);
    }
    // Get One Conversation
    @GetMapping("/{id}")
    public Conversation getConversation(
            @PathVariable Long id) {

        return conversationService.getConversation(id);
    }

    // Rename Conversation
    @PutMapping("/{id}")
    public Conversation renameConversation(
            @PathVariable Long id,
            @RequestParam String title) {

        return conversationService.renameConversation(id, title);
    }
    @PutMapping("/{id}/share")
    public String shareConversation(@PathVariable Long id) {

        Conversation conversation =
                conversationService.enableSharing(id);

        return "http://localhost:3000/share/" + conversation.getShareId();
    }
    // Delete Conversation
    @DeleteMapping("/{id}")
    public String deleteConversation(
            @PathVariable Long id) {

        conversationService.deleteConversation(id);

        return "Conversation Deleted Successfully";
    }
}