package com.thisha_cool.backend.controller;
import com.thisha_cool.backend.dto.ChatMessageResponse;
import com.thisha_cool.backend.entity.ChatMessage;
import com.thisha_cool.backend.service.ChatMessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat/conversations")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    public ChatMessageController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }
    @GetMapping("/share/{shareId}")
    public List<ChatMessageResponse> getSharedConversation(
            @PathVariable String shareId) {

        return chatMessageService.getSharedConversation(shareId);
    }
    // Get all messages of a conversation
    @GetMapping("/{conversationId}/messages")
    public List<ChatMessageResponse> getConversationMessages(
            @PathVariable Long conversationId) {

        return chatMessageService.getConversationMessages(conversationId);
    }

    // Delete all messages of a conversation
    @DeleteMapping("/{conversationId}/messages")
    public String deleteConversationMessages(
            @PathVariable Long conversationId) {

        chatMessageService.deleteConversationMessages(conversationId);

        return "All messages deleted successfully";
    }
}