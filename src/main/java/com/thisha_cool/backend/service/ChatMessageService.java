package com.thisha_cool.backend.service;

import com.thisha_cool.backend.entity.ChatMessage;
import com.thisha_cool.backend.entity.Conversation;
import com.thisha_cool.backend.repository.ChatMessageRepository;
import org.springframework.stereotype.Service;
import com.thisha_cool.backend.dto.ChatMessageResponse;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ConversationService conversationService;

    public ChatMessageService(ChatMessageRepository chatMessageRepository,
                              ConversationService conversationService) {

        this.chatMessageRepository = chatMessageRepository;
        this.conversationService = conversationService;
    }

    // Save Question & Answer
    public ChatMessage saveMessage(Long conversationId,
                                   String question,
                                   String answer) {

        Conversation conversation =
                conversationService.getConversation(conversationId);

        ChatMessage message = ChatMessage.builder()
                .question(question)
                .answer(answer)
                .askedAt(LocalDateTime.now())
                .conversation(conversation)
                .build();

        return chatMessageRepository.save(message);
    }

    // Get complete chat history
    // Internal use - returns entities
    public List<ChatMessage> getConversationMessageEntities(Long conversationId) {

        return chatMessageRepository
                .findByConversationIdOrderByAskedAtAsc(conversationId);
    }

    // API use - returns DTOs
    public List<ChatMessageResponse> getConversationMessages(Long conversationId) {

        return getConversationMessageEntities(conversationId)
                .stream()
                .map(message -> ChatMessageResponse.builder()
                        .id(message.getId())
                        .question(message.getQuestion())
                        .answer(message.getAnswer())
                        .askedAt(message.getAskedAt())
                        .build())
                .toList();
    }
    public String buildConversationContext(Long conversationId) {

        List<ChatMessage> messages =
                chatMessageRepository.findByConversationIdOrderByAskedAtAsc(conversationId);

        StringBuilder context = new StringBuilder();

        int start = Math.max(0, messages.size() - 5);

        for (int i = start; i < messages.size(); i++) {

            context.append("User: ")
                    .append(messages.get(i).getQuestion())
                    .append("\n");
        }

        return context.toString();
    }
    // Delete all messages in one conversation
    public void deleteConversationMessages(Long conversationId) {

        List<ChatMessage> messages =
                getConversationMessageEntities(conversationId);

        chatMessageRepository.deleteAll(messages);
    }
}