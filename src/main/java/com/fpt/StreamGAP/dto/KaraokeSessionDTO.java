package com.fpt.StreamGAP.dto;

import lombok.Data;

import java.util.Date;

@Data
public class KaraokeSessionDTO {
    private Integer sessionId;
    private Integer userId;
    private Integer songId;
    private String recordingUrl;
    private Date createdAt;
}
