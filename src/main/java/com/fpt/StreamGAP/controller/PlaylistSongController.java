package com.fpt.StreamGAP.controller;

import com.fpt.StreamGAP.dto.FavoriteSongDTO;
import com.fpt.StreamGAP.dto.PlaylistSongDTO;
import com.fpt.StreamGAP.dto.ReqRes;
import com.fpt.StreamGAP.entity.Playlist;
import com.fpt.StreamGAP.entity.PlaylistSong;
import com.fpt.StreamGAP.entity.Song;
import com.fpt.StreamGAP.entity.User;
import com.fpt.StreamGAP.service.PlaylistService;
import com.fpt.StreamGAP.service.PlaylistSongService;
import com.fpt.StreamGAP.service.SongService;
import com.fpt.StreamGAP.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/playlistsongs")
public class PlaylistSongController {

    @Autowired
    private PlaylistSongService playlistSongService;

    @Autowired
    private SongService songService;

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private UserManagementService userManagementService;
    @GetMapping
    public ReqRes getAllPlaylistSongs() {
        List<PlaylistSong> playlistSongs = playlistSongService.getAllPlaylistSongsForCurrentUser();

        List<FavoriteSongDTO.PlaylistSongDTO> playlistSongDTOs = playlistSongs.stream()
                .map(playlistSong -> {
                    FavoriteSongDTO.PlaylistSongDTO dto = new FavoriteSongDTO.PlaylistSongDTO();
                    dto.setId(playlistSong.getId());
                    dto.setSong(playlistSong.getSong());
                    dto.setAdded_at(playlistSong.getAdded_at());
                    return dto;
                })
                .collect(Collectors.toList());

        ReqRes response = new ReqRes();
        response.setStatusCode(200);
        response.setMessage("Playlist songs retrieved successfully");
        response.setPlaylistSongList(playlistSongDTOs);
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReqRes> getPlaylistSongById(@PathVariable Integer id) {
        return playlistSongService.getPlaylistSongByIdForCurrentUser(id)
                .map(playlistSong -> {
                    FavoriteSongDTO.PlaylistSongDTO dto = new FavoriteSongDTO.PlaylistSongDTO();
                    dto.setId(playlistSong.getId());
                    dto.setSong(playlistSong.getSong());
                    dto.setAdded_at(playlistSong.getAdded_at());

                    ReqRes response = new ReqRes();
                    response.setStatusCode(200);
                    response.setMessage("Playlist song retrieved successfully");
                    response.setPlaylistSongList(List.of(dto));
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    ReqRes response = new ReqRes();
                    response.setStatusCode(404);
                    response.setMessage("Playlist song not found");
                    return ResponseEntity.status(404).body(response);
                });
    }

    @PostMapping
    public ResponseEntity<PlaylistSong> createPlaylistSong(@RequestBody PlaylistSongDTO playlistSongDTO) {
        if (playlistSongDTO.getPlaylist() == null || playlistSongDTO.getPlaylist().getPlaylistId() == null) {
            throw new IllegalArgumentException("Playlist ID cannot be null when creating a PlaylistSong.");
        }

        if (playlistSongDTO.getSong() == null || playlistSongDTO.getSong().getSongId() == null) {
            throw new IllegalArgumentException("Song ID cannot be null when creating a PlaylistSong.");
        }
        Playlist playlist = playlistService.getPlaylistsByIdForCurrentUser(playlistSongDTO.getPlaylist().getPlaylistId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Playlist ID"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedInUser = (User) authentication.getPrincipal();
        int userId = loggedInUser.getUser_id();
        Integer userIdFromService = userManagementService.getUsersById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User ID"));
        PlaylistSong playlistSong = new PlaylistSong();
        playlistSong.setPlaylist(playlist);
        User user = new User();
        user.setUser_id(userIdFromService);
        playlistSong.setCreatedBy(user);
        Song song = songService.getSongByIdForCurrentUser(playlistSongDTO.getSong().getSongId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Song ID"));
        playlistSong.setSong(song);

        playlistSong.setAdded_at(new java.sql.Date(System.currentTimeMillis()));

        PlaylistSong savedPlaylistSong = playlistSongService.save(playlistSong);
        return ResponseEntity.ok(savedPlaylistSong);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReqRes> updatePlaylistSong(@PathVariable Integer id, @RequestBody PlaylistSong playlistSong) {
        return playlistSongService.getPlaylistSongByIdForCurrentUser(id)
                .map(existingPlaylistSong -> {
                    playlistSong.setId(id);
                    PlaylistSong updatedPlaylistSong = playlistSongService.savePlaylistSong(playlistSong);

                    FavoriteSongDTO.PlaylistSongDTO dto = new FavoriteSongDTO.PlaylistSongDTO();
                    dto.setId(updatedPlaylistSong.getId());
                    dto.setSong(updatedPlaylistSong.getSong());
                    dto.setAdded_at(updatedPlaylistSong.getAdded_at());

                    ReqRes response = new ReqRes();
                    response.setStatusCode(200);
                    response.setMessage("Playlist song updated successfully");
                    response.setPlaylistSongList(List.of(dto));
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    ReqRes response = new ReqRes();
                    response.setStatusCode(404);
                    response.setMessage("Playlist song not found");
                    return ResponseEntity.status(404).body(response);
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReqRes> deletePlaylistSong(@PathVariable Integer id) {
        if (playlistSongService.getPlaylistSongByIdForCurrentUser(id).isPresent()) {
            playlistSongService.deletePlaylistSongForCurrentUser(id);
            ReqRes response = new ReqRes();
            response.setStatusCode(204);
            response.setMessage("Playlist song deleted successfully");
            return ResponseEntity.noContent().build();
        } else {
            ReqRes response = new ReqRes();
            response.setStatusCode(404);
            response.setMessage("Playlist song not found");
            return ResponseEntity.status(404).body(response);
        }
    }
}
