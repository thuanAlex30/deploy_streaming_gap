package com.fpt.StreamGAP.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fpt.StreamGAP.entity.Song;
import lombok.Data;
import com.fpt.StreamGAP.entity.User;
import jakarta.persistence.*;

import java.sql.Date;
@Data
public class PlaylistDTO {
    private Integer playlist_id;
    private User user;
    private String title;
    private Date created_at;
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UserDTO {
        private Integer user_id;
        private String username;
        private String email;
        private String avatar_url;
        private String role;
    }

}
