package com.fpt.StreamGAP.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private Integer commentId;
    private Integer userId;
    private Integer songId;
    private String content;
    private LocalDateTime createdAt;
}

