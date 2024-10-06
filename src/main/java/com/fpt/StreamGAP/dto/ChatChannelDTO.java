package com.fpt.StreamGAP.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Date;

@Data
public class ChatChannelDTO {
    private Integer channel_id;
    private String channel_name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date created_at;
}
