package com.fpt.StreamGAP.controller;

import com.fpt.StreamGAP.dto.ChatChannelDTO;
import com.fpt.StreamGAP.dto.PlaylistDTO;
import com.fpt.StreamGAP.dto.ReqRes;
import com.fpt.StreamGAP.entity.ChatChannel;
import com.fpt.StreamGAP.entity.Playlist;
import com.fpt.StreamGAP.service.ChatChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import  java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ChatChannel")
public class ChatChannelController {
    @Autowired
    private ChatChannelService chatChannelService;
    @GetMapping
    public ReqRes getAllChatChannel() {
        List<ChatChannel> chatChannels = chatChannelService.getAllChatChannel();

        List<ChatChannelDTO> chatChannelDTOS = chatChannels.stream()
                .map(chatChannel -> {
                    ChatChannelDTO dto = new ChatChannelDTO();
                    dto.setChannel_id(chatChannel.getChannel_id());
                    dto.setChannel_name(chatChannel.getChannel_name());
                    dto.setCreated_at(chatChannel.getCreated_at());
                    return dto;
                })
                .collect(Collectors.toList());

        ReqRes response = new ReqRes();
        response.setStatusCode(200);
        response.setMessage("ChatChannel retrieved successfully");
        response.setChatChannel(chatChannelDTOS);
        return response;
    }
    @GetMapping("/{id}")
    public ResponseEntity<ReqRes>   getChatChannelById(@PathVariable Integer id){
        return  chatChannelService.getChatChannelById(id)
                .map(chatChannel -> {
                    ChatChannelDTO dto = new ChatChannelDTO();
                    dto.setChannel_id(chatChannel.getChannel_id());
                    dto.setChannel_name(chatChannel.getChannel_name());
                    dto.setCreated_at(chatChannel.getCreated_at());
                    ReqRes response = new ReqRes();
                    response.setStatusCode(200);
                    response.setMessage("ChatChannel  retrieved successfully");
                    response.setChatChannel(List.of(dto));
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    ReqRes response = new ReqRes();
                    response.setStatusCode(404);
                    response.setMessage("ChatChannel  not found");
                    return ResponseEntity.status(404).body(response);
                });
    }
    @PostMapping
    public ResponseEntity<ReqRes> createChatChannel(@RequestBody ChatChannel chatChannel) {
        ReqRes response = new ReqRes();
        try {
            ChatChannel savedChatChannel = chatChannelService.saveChatChannel(chatChannel);

            ChatChannelDTO dto = new ChatChannelDTO();
            dto.setChannel_id(savedChatChannel.getChannel_id());
            dto.setChannel_name(savedChatChannel.getChannel_name());
            dto.setCreated_at(savedChatChannel.getCreated_at());

            response.setStatusCode(200);
            response.setMessage("ChatChannel created successfully");
            response.setChatChannel(List.of(dto));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReqRes> updateChatChannel(@PathVariable Integer id, @RequestBody ChatChannel chatChannel) {
        return chatChannelService.getChatChannelById(id)
                .map(exitingChatChannel -> {
                    chatChannel.setChannel_id(id);
                    ChatChannel updatedChatChannel = chatChannelService.saveChatChannel(chatChannel);

                    ChatChannelDTO dto = new ChatChannelDTO();
                    dto.setChannel_id(updatedChatChannel.getChannel_id());
                    dto.setChannel_name(updatedChatChannel.getChannel_name());
                    dto.setCreated_at((updatedChatChannel.getCreated_at()));
                    ReqRes response = new ReqRes();
                    response.setStatusCode(200);
                    response.setMessage("ChatChannel  updated successfully");
                    response.setChatChannel(List.of(dto));
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    ReqRes response = new ReqRes();
                    response.setStatusCode(404);
                    response.setMessage("ChatChannel  not found");
                    return ResponseEntity.status(404).body(response);
                });
    }
        @DeleteMapping("/{id}")
        public ResponseEntity<ReqRes> deleteChatChannel(@PathVariable Integer id) {
            if (chatChannelService.getChatChannelById(id).isPresent()) {
                chatChannelService.deleteChatChannel(id);
                ReqRes response = new ReqRes();
                response.setStatusCode(200);
                response.setMessage("ChatChannel  deleted successfully");
                return ResponseEntity.ok(response);
            } else {
                ReqRes response = new ReqRes();
                response.setStatusCode(404);
                response.setMessage("ChatChannel song not found");
                return ResponseEntity.status(404).body(response);
            }
        }
    }

