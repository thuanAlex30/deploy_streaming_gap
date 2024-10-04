package com.fpt.StreamGAP.service;

import com.fpt.StreamGAP.entity.Album;
import com.fpt.StreamGAP.entity.Playlist;
import com.fpt.StreamGAP.repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    public List<Playlist> getAllPlaylist() {
        return playlistRepository.findAll();
    }

    public Optional<Playlist> getPlaylistById(Integer id) {
        return playlistRepository.findById(id);
    }

    public Playlist savePlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    public void deletePlaylist(Integer id) {
        playlistRepository.deleteById(id);
    }
}
