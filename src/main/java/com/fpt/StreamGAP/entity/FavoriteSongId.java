package com.fpt.StreamGAP.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FavoriteSongId implements Serializable {
    private Integer userId; // Match the type with your User entity ID type
    private Integer songId; // Match the type with your Song entity ID type

    // Default constructor
    public FavoriteSongId() {}

    // Getters and Setters
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

    // Override equals() and hashCode() methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FavoriteSongId)) return false;
        FavoriteSongId that = (FavoriteSongId) o;
        return userId.equals(that.userId) && songId.equals(that.songId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, songId);
    }
}
