package com.fpt.StreamGAP.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Date;

@Entity
@Table(name = "Comments")
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer comment_id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "song_id", nullable = false)
    private Song song;

    private String content;
    private Date created_at;
}
