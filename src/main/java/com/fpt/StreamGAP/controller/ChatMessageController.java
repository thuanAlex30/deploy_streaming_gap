package com.fpt.StreamGAP.controller;

import com.fpt.StreamGAP.dto.ChatMessageDTO;
import com.fpt.StreamGAP.dto.ReqRes;
import com.fpt.StreamGAP.entity.ChatMessage;
import com.fpt.StreamGAP.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chatmessages")
public class ChatMessageController {

    @Autowired
    private ChatMessageService chatMessageService;

    @GetMapping
    public ReqRes getAllChatMessages() {
        List<ChatMessage> chatMessages = chatMessageService.getAllChatMessages();

        List<ChatMessageDTO> chatMessageDTOs = chatMessages.stream()
                .map(chatMessage -> {
                    ChatMessageDTO dto = new ChatMessageDTO();
                    dto.setMessageId(chatMessage.getMessage_id());
                    dto.setContent(chatMessage.getContent());
                    dto.setCreatedAt(chatMessage.getCreated_at());
                    dto.setChannelId(chatMessage.getChannel().getChannel_id());
                    dto.setUserId(chatMessage.getUser().getUser_id());
                    return dto;
                })
                .collect(Collectors.toList());

        ReqRes response = new ReqRes();
        response.setStatusCode(200);
        response.setMessage("Chat messages retrieved successfully");
        response.setChatMessageList(chatMessageDTOs);
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReqRes> getChatMessageById(@PathVariable Integer id) {
        return chatMessageService.getChatMessageById(id)
                .map(chatMessage -> {
                    ChatMessageDTO dto = new ChatMessageDTO();
                    dto.setMessageId(chatMessage.getMessage_id());
                    dto.setContent(chatMessage.getContent());
                    dto.setCreatedAt(chatMessage.getCreated_at());
                    dto.setChannelId(chatMessage.getChannel().getChannel_id());
                    dto.setUserId(chatMessage.getUser().getUser_id());

                    ReqRes response = new ReqRes();
                    response.setStatusCode(200);
                    response.setMessage("Chat message retrieved successfully");
                    response.setChatMessageList(List.of(dto));
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    ReqRes response = new ReqRes();
                    response.setStatusCode(404);
                    response.setMessage("Chat message not found");
                    return ResponseEntity.status(404).body(response);
                });
    }

    @PostMapping
    public ReqRes createChatMessage(@RequestBody ChatMessage chatMessage) {
        ChatMessage savedChatMessage = chatMessageService.saveChatMessage(chatMessage);

        ChatMessageDTO dto = new ChatMessageDTO();
        dto.setMessageId(savedChatMessage.getMessage_id());
        dto.setContent(savedChatMessage.getContent());
        dto.setCreatedAt(savedChatMessage.getCreated_at());
        dto.setChannelId(savedChatMessage.getChannel().getChannel_id());
        dto.setUserId(savedChatMessage.getUser().getUser_id());

        ReqRes response = new ReqRes();
        response.setStatusCode(201);
        response.setMessage("Chat message created successfully");
        response.setChatMessageList(List.of(dto));
        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReqRes> updateChatMessage(@PathVariable Integer id, @RequestBody ChatMessage chatMessage) {
        return chatMessageService.getChatMessageById(id)
                .map(existingChatMessage -> {
                    chatMessage.setMessage_id(id);
                    ChatMessage updatedChatMessage = chatMessageService.saveChatMessage(chatMessage);

                    ChatMessageDTO dto = new ChatMessageDTO();
                    dto.setMessageId(updatedChatMessage.getMessage_id());
                    dto.setContent(updatedChatMessage.getContent());
                    dto.setCreatedAt(updatedChatMessage.getCreated_at());
                    dto.setChannelId(updatedChatMessage.getChannel().getChannel_id());
                    dto.setUserId(updatedChatMessage.getUser().getUser_id());

                    ReqRes response = new ReqRes();
                    response.setStatusCode(200);
                    response.setMessage("Chat message updated successfully");
                    response.setChatMessageList(List.of(dto));
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    ReqRes response = new ReqRes();
                    response.setStatusCode(404);
                    response.setMessage("Chat message not found");
                    return ResponseEntity.status(404).body(response);
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReqRes> deleteChatMessage(@PathVariable Integer id) {
        if (chatMessageService.getChatMessageById(id).isPresent()) {
            chatMessageService.deleteChatMessage(id);
            ReqRes response = new ReqRes();
            response.setStatusCode(204);
            response.setMessage("Chat message deleted successfully");
            return ResponseEntity.noContent().build();
        } else {
            ReqRes response = new ReqRes();
            response.setStatusCode(404);
            response.setMessage("Chat message not found");
            return ResponseEntity.status(404).body(response);
        }
    }
}
