package com.fpt.StreamGAP.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fpt.StreamGAP.entity.Song;
import lombok.Data;
import java.sql.Date;

@Data
public class FavoriteSongDTO {
    private Integer userId;
    private Integer songId;
    private Date markedAt;

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PlaylistSongDTO {
        private Integer id;
        private Song song;
        private java.util.Date added_at;
    }
}
