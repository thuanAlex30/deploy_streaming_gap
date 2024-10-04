package com.fpt.StreamGAP.controller;

import com.fpt.StreamGAP.dto.FavoriteSongDTO;
import com.fpt.StreamGAP.dto.ReqRes;
import com.fpt.StreamGAP.entity.PlaylistSong;
import com.fpt.StreamGAP.service.PlaylistSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/playlistsongs")
public class PlaylistSongController {

    @Autowired
    private PlaylistSongService playlistSongService;

    @GetMapping
    public ReqRes getAllPlaylistSongs() {
        List<PlaylistSong> playlistSongs = playlistSongService.getAllPlaylistSongs();

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
        return playlistSongService.getPlaylistSongById(id)
                .map(playlistSong -> {
                    FavoriteSongDTO.PlaylistSongDTO dto = new FavoriteSongDTO.PlaylistSongDTO();
                    dto.setId(playlistSong.getId());
                    dto.setSong(playlistSong.getSong()); //
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
    public ReqRes createPlaylistSong(@RequestBody PlaylistSong playlistSong) {
        PlaylistSong savedPlaylistSong = playlistSongService.savePlaylistSong(playlistSong);

        FavoriteSongDTO.PlaylistSongDTO dto = new FavoriteSongDTO.PlaylistSongDTO();
        dto.setId(savedPlaylistSong.getId());
        dto.setSong(savedPlaylistSong.getSong());
        dto.setAdded_at(savedPlaylistSong.getAdded_at());

        ReqRes response = new ReqRes();
        response.setStatusCode(201);
        response.setMessage("Playlist song created successfully");
        response.setPlaylistSongList(List.of(dto));
        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReqRes> updatePlaylistSong(@PathVariable Integer id, @RequestBody PlaylistSong playlistSong) {
        return playlistSongService.getPlaylistSongById(id)
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
        if (playlistSongService.getPlaylistSongById(id).isPresent()) {
            playlistSongService.deletePlaylistSong(id);
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
