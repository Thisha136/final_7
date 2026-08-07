package com.thisha_cool.backend.service;
import com.thisha_cool.backend.entity.Conversation;
import com.thisha_cool.backend.repository.ConversationRepository;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.time.LocalDateTime;
import java.util.List;
import com.thisha_cool.backend.repository.ChatMessageRepository;
@Service
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final ChatMessageRepository chatMessageRepository;
    public ConversationService(
            ConversationRepository conversationRepository,
            ChatMessageRepository chatMessageRepository
            ) {

        this.conversationRepository = conversationRepository;
        this.chatMessageRepository = chatMessageRepository;

    }
    public List<Conversation> searchConversationHistory(String keyword) {

        return chatMessageRepository.searchConversationHistory(keyword);
    }
    // Create New Conversation

    public Conversation enableSharing(Long id) {

        Conversation conversation = getConversation(id);

        conversation.setIsPublic(true);

        return conversationRepository.save(conversation);
    }
    public Conversation getConversationByShareId(String shareId) {

        Conversation conversation = conversationRepository
                .findByShareId(shareId)
                .orElseThrow(() ->
                        new RuntimeException("Shared conversation not found"));

        if (!Boolean.TRUE.equals(conversation.getIsPublic())) {
            throw new RuntimeException("Conversation is not shared");
        }

        return conversation;
    }
    public Conversation createConversation(String title) {

        Conversation conversation = Conversation.builder()
                .title(title)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .shareId(UUID.randomUUID().toString())
                .isPublic(false)
                .build();

        return conversationRepository.save(conversation);
    }

    // Get All Conversations
    public List<Conversation> getAllConversations() {

        return conversationRepository.findAllByOrderByUpdatedAtDesc();
    }
    // Get One Conversation
    public Conversation getConversation(Long id) {

        return conversationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Conversation not found"));
    }

    // Delete Conversation
    public void deleteConversation(Long id)
    {

        Conversation conversation = getConversation(id);

        // Delete all messages
        chatMessageRepository.deleteAll(
                chatMessageRepository.findByConversationIdOrderByAskedAtAsc(id)
        );

        // Delete conversation
        conversationRepository.delete(conversation);
    }
    public Conversation save(Conversation conversation) {
        return conversationRepository.save(conversation);
    }
    // Rename Conversation
    public Conversation renameConversation(Long id, String title) {

        Conversation conversation = getConversation(id);

        conversation.setTitle(title);
        conversation.setUpdatedAt(LocalDateTime.now());

        return conversationRepository.save(conversation);
    }
}