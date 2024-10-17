package com.fpt.StreamGAP.controller;

import com.fpt.StreamGAP.dto.PlaylistDTO;
import com.fpt.StreamGAP.dto.ReqRes;
import com.fpt.StreamGAP.entity.Playlist;
import com.fpt.StreamGAP.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @GetMapping
    public ReqRes getAllPlaylistsForCurrentUser() {
        List<Playlist> playlists = playlistService.getAllPlaylistsForCurrentUser();

        List<PlaylistDTO> playlistDTOS = playlists.stream()
                .map(playList -> {
                    PlaylistDTO dto = new PlaylistDTO();
                    dto.setPlaylistId(playList.getPlaylistId());
                    dto.setTitle(playList.getTitle());
                    dto.setCreated_at(playList.getCreated_at());
                    dto.setUser_id(playList.getUser().getUser_id());
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
    public ResponseEntity<ReqRes> getPlaylistsByIdForCurrentUser(@PathVariable Integer id) {
        return playlistService.getPlaylistsByIdForCurrentUser(id)
                .map(playList -> {
                    PlaylistDTO dto = new PlaylistDTO();
                    dto.setPlaylistId(playList.getPlaylistId());
                    dto.setTitle(playList.getTitle());
                    dto.setCreated_at(playList.getCreated_at());
                    dto.setUser_id(playList.getUser().getUser_id());


                    ReqRes response = new ReqRes();
                    response.setStatusCode(200);
                    response.setMessage("Playlist retrieved successfully");
                    response.setPlayList(List.of(dto));
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    ReqRes response = new ReqRes();
                    response.setStatusCode(404);
                    response.setMessage("Playlist not found");
                    return ResponseEntity.status(404).body(response);
                });
    }


    @PostMapping
    public ResponseEntity<ReqRes> createPlaylist(@RequestBody Playlist playlist) {
        ReqRes response = new ReqRes();
        try {
            playlist.setCreated_at(new Date());
            Playlist savedPlaylist = playlistService.savePlaylist(playlist);

            PlaylistDTO dto = new PlaylistDTO();
            dto.setPlaylistId(savedPlaylist.getPlaylistId());
            dto.setTitle(savedPlaylist.getTitle());
            dto.setCreated_at(savedPlaylist.getCreated_at());
            dto.setUser_id(savedPlaylist.getUser().getUser_id());

            response.setStatusCode(201);
            response.setMessage("Playlist created successfully");
            response.setPlayList(List.of(dto));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalStateException e) {
            response.setStatusCode(400);
            response.setMessage("Title already exists: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReqRes> updatePlaylists(@PathVariable Integer id, @RequestBody Playlist playlist) {
        ReqRes response = new ReqRes();
        try {
            return playlistService.getPlaylistsByIdForCurrentUser(id)
                    .map(existingPlaylist -> {
                        existingPlaylist.setTitle(playlist.getTitle());
                        Playlist updatedPlaylist = playlistService.savePlaylist(existingPlaylist);

                        PlaylistDTO dto = new PlaylistDTO();
                        dto.setPlaylistId(updatedPlaylist.getPlaylistId());
                        dto.setUser_id(updatedPlaylist.getUser().getUser_id());
                        dto.setTitle(updatedPlaylist.getTitle());
                        dto.setCreated_at(updatedPlaylist.getCreated_at());

                        response.setStatusCode(200);
                        response.setMessage("Playlist updated successfully");
                        response.setPlayList(List.of(dto));
                        return ResponseEntity.ok(response);
                    })
                    .orElseGet(() -> {
                        response.setStatusCode(404);
                        response.setMessage("Playlist not found");
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                    });
        } catch (IllegalStateException e) {
            response.setStatusCode(400);
            response.setMessage("Title already exists: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReqRes> deletePlaylistsForCurrentUser(@PathVariable Integer id) {
        return playlistService.getPlaylistsByIdForCurrentUser(id)
                .map(playlist -> {
                    playlistService.deletePlaylistsForCurrentUser(id);
                    ReqRes response = new ReqRes();
                    response.setStatusCode(200);
                    response.setMessage("Playlist deleted successfully");
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    ReqRes response = new ReqRes();
                    response.setStatusCode(404);
                    response.setMessage("Playlist not found");
                    return ResponseEntity.status(404).body(response);
                });
    }
}
