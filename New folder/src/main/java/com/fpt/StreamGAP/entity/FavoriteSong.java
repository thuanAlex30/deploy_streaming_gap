package com.fpt.StreamGAP.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Date;

@Entity
@Table(name = "Favorite_Songs")
@Data
public class FavoriteSong {

    @EmbeddedId
    private FavoriteSongId id;

    @ManyToOne
    @MapsId("user_id")
    private User user;

    @ManyToOne
    @MapsId("song_id")
    private Song song;

    private Date marked_at;
}
