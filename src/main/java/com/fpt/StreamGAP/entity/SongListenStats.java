package com.fpt.StreamGAP.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "Song_Listen_Stats")
@Data
public class SongListenStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer listen_id;

    @ManyToOne
    @JoinColumn(name = "song_id", nullable = false)
    private Song song;

    @Column(name = "listen_count", nullable = false)
    private Integer listen_count = 0;

    @Column(name = "recorded_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date recorded_at;

    public SongListenStats() {
        this.recorded_at = new Date();
    }


    public Integer getListen_id() {
        return listen_id;
    }

    public void setListen_id(Integer listen_id) {
        this.listen_id = listen_id;
    }


    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }


    public Integer getListen_count() {
        return listen_count;
    }

    public void setListen_count(Integer listen_count) {
        this.listen_count = listen_count;
    }


    public Date getRecorded_at() {
        return recorded_at;
    }

    public void setRecorded_at(Date recorded_at) {
        this.recorded_at = recorded_at;
    }
}
