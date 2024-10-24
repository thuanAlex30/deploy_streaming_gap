package com.fpt.StreamGAP.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fpt.StreamGAP.entity.Song;
import lombok.Data;
import java.util.Date;

@Data
public class FavoriteSongDTO {
    private Integer userId;
    private Integer songId;
    private Date markedAt;

    public Integer getSongId() {
        return songId;
    }

    public void setSongId(Integer songId) {
        this.songId = songId;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PlaylistSongDTO {
        private Integer id;
        private Song song;
        private java.util.Date added_at;
    }
}
