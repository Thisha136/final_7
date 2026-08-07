package com.thisha_cool.backend.repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.thisha_cool.backend.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import com.thisha_cool.backend.entity.Conversation;
import java.util.List;
import com.thisha_cool.backend.entity.Conversation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByConversationIdOrderByAskedAtAsc(Long conversationId);

    @Query("""
    SELECT DISTINCT m.conversation
    FROM ChatMessage m
    WHERE
    LOWER(m.question) LIKE LOWER(CONCAT('%', :keyword, '%'))
    OR
    LOWER(m.answer) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    List<Conversation> searchConversationHistory(
            @Param("keyword") String keyword);
}