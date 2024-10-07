package com.fpt.StreamGAP.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fpt.StreamGAP.entity.Song;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaylistSongDTO {
    private Integer id;
    private Song song;
    private Date added_at;
}