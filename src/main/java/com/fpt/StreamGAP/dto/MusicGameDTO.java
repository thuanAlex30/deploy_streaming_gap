package com.fpt.StreamGAP.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class MusicGameDTO {
    private Integer gameId;
    private Integer userId;
    private Integer score;
    private String gameType;
    private Date playedAt;
}