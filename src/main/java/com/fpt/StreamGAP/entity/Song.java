package com.fpt.StreamGAP.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Date;

@Entity
@Table(name = "Songs")
@Data
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer song_id;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    private String title;
    private String genre;
    private Integer duration;
    private String audio_file_url;
    private String lyrics;
    private Date created_at;
}
