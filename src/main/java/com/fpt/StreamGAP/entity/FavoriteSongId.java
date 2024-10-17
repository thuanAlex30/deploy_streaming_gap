package com.fpt.StreamGAP.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FavoriteSongId implements Serializable {
    private Integer userId;
    private Integer songId;

    // Default constructor
    public FavoriteSongId() {}

    // Constructor with parameters
    public FavoriteSongId(Integer userId, Integer songId) {
        this.userId = userId;
        this.songId = songId;
    }

    // Getters and setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSongId() {
        return songId;
    }

    public void setSongId(Integer songId) {
        this.songId = songId;
    }

    // Override equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FavoriteSongId)) return false;
        FavoriteSongId that = (FavoriteSongId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(songId, that.songId);
    }

    // Override hashCode
    @Override
    public int hashCode() {
        return Objects.hash(userId, songId);
    }
}
