package com.thisha_cool.backend.repository;

import com.thisha_cool.backend.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository
        extends JpaRepository<Conversation, Long> {
}