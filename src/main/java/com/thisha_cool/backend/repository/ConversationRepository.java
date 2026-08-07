package com.thisha_cool.backend.repository;

import com.thisha_cool.backend.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository
        extends JpaRepository<Conversation, Long> {

    List<Conversation> findAllByOrderByUpdatedAtDesc();

    Optional<Conversation> findByShareId(String shareId);
}