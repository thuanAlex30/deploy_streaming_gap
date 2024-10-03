package com.fpt.StreamGAP.service;

import com.fpt.StreamGAP.entity.PlaylistSong;
import com.fpt.StreamGAP.repository.PlaylistSongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaylistSongService {

    @Autowired
    private PlaylistSongRepository playlistSongRepository;

    public List<PlaylistSong> getAllPlaylistSongs() {
        return playlistSongRepository.findAll();
    }

    public Optional<PlaylistSong> getPlaylistSongById(Integer id) {
        return playlistSongRepository.findById(id);
    }

    public PlaylistSong savePlaylistSong(PlaylistSong playlistSong) {
        return playlistSongRepository.save(playlistSong);
    }

    public void deletePlaylistSong(Integer id) {
        playlistSongRepository.deleteById(id);
    }
}
