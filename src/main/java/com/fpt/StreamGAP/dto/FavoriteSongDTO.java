package com.fpt.StreamGAP.dto;

import lombok.Data;
import java.sql.Date;

@Data
public class FavoriteSongDTO {
    private Integer userId;
    private Integer songId;
    private Date markedAt;
}
