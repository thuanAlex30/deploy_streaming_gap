package com.fpt.StreamGAP.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fpt.StreamGAP.entity.Playlist;
import com.fpt.StreamGAP.entity.Song;
import com.fpt.StreamGAP.entity.User;
import lombok.Data;
import lombok.Getter;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaylistSongDTO {
    private Integer id;
    private Song song;
    @Getter
    private PlaylistDTO playlist;
    private UserDTO user;



    public void setPlaylist(PlaylistDTO playlist) {
        this.playlist = playlist;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

}
