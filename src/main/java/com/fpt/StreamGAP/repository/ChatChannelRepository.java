package com.fpt.StreamGAP.repository;

import com.fpt.StreamGAP.entity.Artist;
import com.fpt.StreamGAP.entity.ChatChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatChannelRepository extends JpaRepository<ChatChannel, Integer> {
}
