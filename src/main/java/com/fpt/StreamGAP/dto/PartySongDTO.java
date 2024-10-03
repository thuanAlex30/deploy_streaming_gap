package com.fpt.StreamGAP.dto;

import com.fpt.StreamGAP.entity.PartySongId;
import com.fpt.StreamGAP.entity.Song; // Assuming this entity exists
import lombok.Data;

import java.sql.Date;

@Data
public class PartySongDTO {
    private PartySongId id;
    private Integer partyId;
    private Integer songId;
    private Song song;
    private Date addedAt;
}
