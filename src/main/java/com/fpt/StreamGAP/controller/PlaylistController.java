package com.fpt.StreamGAP.controller;

import com.fpt.StreamGAP.dto.FavoriteSongDTO;
import com.fpt.StreamGAP.dto.PlaylistDTO;
import com.fpt.StreamGAP.dto.ReqRes;
import com.fpt.StreamGAP.entity.Playlist;
import com.fpt.StreamGAP.entity.PlaylistSong;
import com.fpt.StreamGAP.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @GetMapping
    public ReqRes getAllPlaylist() {
        List<Playlist> playlists = playlistService.getAllPlaylist();

        List<PlaylistDTO> playlistDTOS = playlists.stream()
                .map(playList -> {
                    PlaylistDTO dto = new PlaylistDTO();
                    dto.setUser(playList.getUser());
                    dto.setPlaylist_id(playList.getPlaylist_id());
                    dto.setTitle(playList.getTitle());
                    dto.setCreated_at(playList.getCreated_at());
                    return dto;
                })
                .collect(Collectors.toList());

        ReqRes response = new ReqRes();
        response.setStatusCode(200);
        response.setMessage("Playlist retrieved successfully");
        response.setPlayList(playlistDTOS);
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReqRes> getPlaylistById(@PathVariable Integer id) {
        return playlistService.getPlaylistById(id)
                .map(playList -> {
                    PlaylistDTO dto = new PlaylistDTO();
                    dto.setUser(playList.getUser());
                    dto.setPlaylist_id(playList.getPlaylist_id());
                    dto.setTitle(playList.getTitle());
                    dto.setCreated_at(playList.getCreated_at());

                    ReqRes response = new ReqRes();
                    response.setStatusCode(200);
                    response.setMessage("Playlist  retrieved successfully");
                    response.setPlayList(List.of(dto));
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    ReqRes response = new ReqRes();
                    response.setStatusCode(404);
                    response.setMessage("Playlist  not found");
                    return ResponseEntity.status(404).body(response);
                });
    }

    @PostMapping
    public ResponseEntity<ReqRes> createPlaylist(@RequestBody Playlist playlist) {
        ReqRes response = new ReqRes();
        try {
            Playlist savedPlaylist = playlistService.savePlaylist(playlist);
            playlist.setCreated_at(new Date(System.currentTimeMillis()));
            PlaylistDTO dto = new PlaylistDTO();
            dto.setUser(savedPlaylist.getUser());
            dto.setPlaylist_id(savedPlaylist.getPlaylist_id());
            dto.setTitle(savedPlaylist.getTitle());
            dto.setCreated_at(savedPlaylist.getCreated_at());

            response.setStatusCode(200);
            response.setMessage("Playlist created successfully");
            response.setPlayList(List.of(dto));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<ReqRes> updatePlaylist(@PathVariable Integer id, @RequestBody Playlist playlist) {
        return playlistService.getPlaylistById(id)
                .map(existingPlaylist -> {
                    playlist.setPlaylist_id(id);
                    playlist.setCreated_at(new Date(System.currentTimeMillis()));
                    Playlist updatedPlaylist = playlistService.savePlaylist(playlist);

                    PlaylistDTO dto = new PlaylistDTO();
                    dto.setUser(updatedPlaylist.getUser());
                    dto.setTitle(updatedPlaylist.getTitle());
                    dto.setPlaylist_id(updatedPlaylist.getPlaylist_id());
                    dto.setCreated_at((updatedPlaylist.getCreated_at()));
                    ReqRes response = new ReqRes();
                    response.setStatusCode(200);
                    response.setMessage("Playlist  updated successfully");
                    response.setPlayList(List.of(dto));
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    ReqRes response = new ReqRes();
                    response.setStatusCode(404);
                    response.setMessage("Playlist  not found");
                    return ResponseEntity.status(404).body(response);
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReqRes> deletePlaylist(@PathVariable Integer id) {
        if (playlistService.getPlaylistById(id).isPresent()) {
            playlistService.deletePlaylist(id);
            ReqRes response = new ReqRes();
            response.setStatusCode(200);
            response.setMessage("Playlist  deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            ReqRes response = new ReqRes();
            response.setStatusCode(404);
            response.setMessage("Playlist song not found");
            return ResponseEntity.status(404).body(response);
        }
    }
}
