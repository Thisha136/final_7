package com.thisha_cool.backend.service;

import com.thisha_cool.backend.entity.Conversation;
import com.thisha_cool.backend.repository.ConversationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConversationService {

    private final ConversationRepository conversationRepository;

    public ConversationService(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    // Create New Conversation
    public Conversation createConversation(String title) {

        Conversation conversation = Conversation.builder()
                .title(title)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return conversationRepository.save(conversation);
    }

    // Get All Conversations
    public List<Conversation> getAllConversations() {
        return conversationRepository.findAll();
    }

    // Get One Conversation
    public Conversation getConversation(Long id) {

        return conversationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Conversation not found"));
    }

    // Delete Conversation
    public void deleteConversation(Long id) {
        conversationRepository.deleteById(id);
    }

    // Rename Conversation
    public Conversation renameConversation(Long id, String title) {

        Conversation conversation = getConversation(id);

        conversation.setTitle(title);
        conversation.setUpdatedAt(LocalDateTime.now());

        return conversationRepository.save(conversation);
    }
}