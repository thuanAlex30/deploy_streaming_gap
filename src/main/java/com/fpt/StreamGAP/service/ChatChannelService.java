package com.fpt.StreamGAP.service;

import com.fpt.StreamGAP.entity.ChatChannel;
import com.fpt.StreamGAP.entity.Playlist;
import com.fpt.StreamGAP.repository.ChatChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ChatChannelService {
    @Autowired
    private ChatChannelRepository chatChannelRepository;
    public List<ChatChannel> getAllChatChannel() {
        return chatChannelRepository.findAll();
    }

    public Optional<ChatChannel> getChatChannelById(Integer id) {
        return chatChannelRepository.findById(id);
    }

    public ChatChannel saveChatChannel(ChatChannel chatChannel) {
        return chatChannelRepository.save(chatChannel);
    }

    public void deleteChatChannel(Integer id) {
        chatChannelRepository.deleteById(id);
    }
}

