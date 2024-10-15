package com.fpt.StreamGAP.dto;

import com.fpt.StreamGAP.entity.PartySongId;
import com.fpt.StreamGAP.entity.Song;
import lombok.Data;

import java.sql.Date;

@Data
public class PartySongDTO {
    private PartySongId id;
    private Integer partyId;
    private Integer songId;
    private Song song;
    private Date addedAt;

    // Default constructor
    public PartySongDTO() {}

    // Constructor with parameters
    public PartySongDTO(PartySongId id, Integer partyId, Integer songId, Song song) {
        this.id = id;
        this.partyId = partyId;
        this.songId = songId;
        this.song = song;
    }
}
