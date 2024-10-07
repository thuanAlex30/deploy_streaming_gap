package com.fpt.StreamGAP.repository;

import com.fpt.StreamGAP.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {
}
