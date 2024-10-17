package com.fpt.StreamGAP.service;

import com.fpt.StreamGAP.entity.Playlist;
import com.fpt.StreamGAP.entity.PlaylistSong;
import com.fpt.StreamGAP.entity.Song;
import com.fpt.StreamGAP.entity.User;
import com.fpt.StreamGAP.repository.PlaylistRepository;
import com.fpt.StreamGAP.repository.PlaylistSongRepository;
import com.fpt.StreamGAP.repository.SongRepository;
import com.fpt.StreamGAP.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaylistSongService {

    @Autowired
    private PlaylistSongRepository playlistSongRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private PlaylistRepository playlistRepository;


    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return null;
    }


    public List<PlaylistSong> getAllPlaylistSongsForCurrentUser() {
        String currentUsername = getCurrentUsername();
        return playlistSongRepository.findByCreatedByUsername(currentUsername);
    }

    public Optional<PlaylistSong> getPlaylistSongByIdForCurrentUser(Integer id) {
        String currentUsername = getCurrentUsername();
        return playlistSongRepository.findByIdAndCreatedByUsername(id, currentUsername);
    }



    @Transactional
    public PlaylistSong savePlaylistSong(PlaylistSong playlistSong) {
        Song song = playlistSong.getSong();
        if (song != null && song.getSongId() == null) {
            song = songRepository.save(song);
            playlistSong.setSong(song);
        }

        Optional<Playlist> playlistOpt = playlistRepository.findById(playlistSong.getPlaylist().getPlaylistId());
        if (playlistOpt.isPresent()) {
            playlistSong.setPlaylist(playlistOpt.get());
        } else {
            throw new IllegalArgumentException("Playlist not found");
        }

        return playlistSongRepository.save(playlistSong);
    }
    public PlaylistSong save(PlaylistSong playlistSong) {
        return playlistSongRepository.save(playlistSong);

    }
    public void deletePlaylistSongForCurrentUser(Integer id) {
        String currentUsername = getCurrentUsername();
        Optional<PlaylistSong> playlistSong = playlistSongRepository.findByIdAndCreatedByUsername(id, currentUsername);
        playlistSong.ifPresent(playlistSongRepository::delete);
    }



}
