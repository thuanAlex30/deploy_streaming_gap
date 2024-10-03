package com.fpt.StreamGAP.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Date;

@Entity
@Table(name = "Chat_Messages")
@Data
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer message_id;

    @ManyToOne
    @JoinColumn(name = "channel_id", nullable = false)
    private ChatChannel channel;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String content;
    private Date created_at;
}
