package com.fpt.StreamGAP.service;

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

    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    public Optional<Playlist> getPlaylistById(Integer id) {
        return playlistRepository.findById(id);
    }

    public Playlist createPlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    public Playlist updatePlaylist(Integer id, Playlist playlistDetails) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Playlist not found with id: " + id));

        playlist.setTitle(playlistDetails.getTitle());
        playlist.setUser(playlistDetails.getUser());
        playlist.setCreated_at(playlistDetails.getCreated_at());

        return playlistRepository.save(playlist);
    }

    public void deletePlaylist(Integer id) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Playlist not found with id: " + id));
        playlistRepository.delete(playlist);
    }
}
