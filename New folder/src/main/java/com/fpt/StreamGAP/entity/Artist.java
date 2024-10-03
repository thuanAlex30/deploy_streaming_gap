package com.fpt.StreamGAP.entity;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "Artists")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer artist_id;
    private String name;
    private String bio;
    private String profile_image_url;
    private Date created_at;

    // No-arg constructor
    public Artist() {
    }

    // All-arg constructor
    public Artist(Integer artist_id, String name, String bio, String profile_image_url, Date created_at) {
        this.artist_id = artist_id;
        this.name = name;
        this.bio = bio;
        this.profile_image_url = profile_image_url;
        this.created_at = created_at;
    }

    // Getter for artist_id
    public Integer getArtist_id() {
        return artist_id;
    }

    // Setter for artist_id
    public void setArtist_id(Integer artist_id) {
        this.artist_id = artist_id;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for bio
    public String getBio() {
        return bio;
    }

    // Setter for bio
    public void setBio(String bio) {
        this.bio = bio;
    }

    // Getter for profile_image_url
    public String getProfile_image_url() {
        return profile_image_url;
    }

    // Setter for profile_image_url
    public void setProfile_image_url(String profile_image_url) {
        this.profile_image_url = profile_image_url;
    }

    // Getter for created_at
    public Date getCreated_at() {
        return created_at;
    }

    // Setter for created_at
    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    // toString method for printing object
    @Override
    public String toString() {
        return "Artist{" +
                "artist_id=" + artist_id +
                ", name='" + name + '\'' +
                ", bio='" + bio + '\'' +
                ", profile_image_url='" + profile_image_url + '\'' +
                ", created_at=" + created_at +
                '}';
    }
}
