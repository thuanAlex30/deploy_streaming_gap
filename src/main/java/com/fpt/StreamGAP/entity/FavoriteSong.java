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
    @MapsId("userId") // Make sure it matches the field name in FavoriteSongId
    @JoinColumn(name = "user_id") // Ensure this matches your database schema
    private User user;

    @ManyToOne
    @MapsId("songId") // Make sure it matches the field name in FavoriteSongId
    @JoinColumn(name = "song_id") // Ensure this matches your database schema
    private Song song;

    private Date markedAt; // Note: Java naming convention is camelCase
}
