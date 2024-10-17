package com.fpt.StreamGAP.controller;

import com.fpt.StreamGAP.dto.FavoriteSongDTO;
import com.fpt.StreamGAP.dto.ReqRes;
import com.fpt.StreamGAP.entity.FavoriteSong;
import com.fpt.StreamGAP.entity.FavoriteSongId;
import com.fpt.StreamGAP.entity.Song;
import com.fpt.StreamGAP.entity.User;
import com.fpt.StreamGAP.repository.SongRepository;
import com.fpt.StreamGAP.repository.UserRepo;
import com.fpt.StreamGAP.service.FavoriteSongService;
import com.fpt.StreamGAP.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/favorite-songs")
public class FavoriteSongController {

    @Autowired
    private FavoriteSongService favoriteSongService;

    @GetMapping
    public ReqRes getAllFavoriteSongs() {
        List<FavoriteSong> favoriteSongs = favoriteSongService.getAllFavoriteSongsForCurrentUser();

        List<FavoriteSongDTO> favoriteSongDTOs = favoriteSongs.stream()
                .map(favoriteSong -> {
                    FavoriteSongDTO dto = new FavoriteSongDTO();
                    dto.setUserId(favoriteSong.getF_id().getUserId());
                    dto.setSongId(favoriteSong.getF_id().getSongId());
                    dto.setMarkedAt(favoriteSong.getMarkedAt());
                    return dto;
                })
                .collect(Collectors.toList());

        ReqRes response = new ReqRes();
        response.setStatusCode(200);
        response.setMessage("Favorite songs retrieved successfully");
        response.setFavoriteSongList(favoriteSongDTOs);
        return response;
    }

    @GetMapping("/{userId}/{songId}")
    public ResponseEntity<ReqRes> getFavoriteSongById(@PathVariable Integer userId, @PathVariable Integer songId) {
        FavoriteSongId favoriteSongId = new FavoriteSongId();
        favoriteSongId.setUserId(userId);
        favoriteSongId.setSongId(songId);

        return favoriteSongService.getFavoriteSongById(favoriteSongId)
                .map(favoriteSong -> {
                    FavoriteSongDTO dto = new FavoriteSongDTO();
                    dto.setUserId(favoriteSong.getF_id().getUserId());
                    dto.setSongId(favoriteSong.getF_id().getSongId());
                    dto.setMarkedAt(favoriteSong.getMarkedAt());

                    ReqRes response = new ReqRes();
                    response.setStatusCode(200);
                    response.setMessage("Favorite song retrieved successfully");
                    response.setFavoriteSongList(List.of(dto));
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    ReqRes response = new ReqRes();
                    response.setStatusCode(404);
                    response.setMessage("Favorite song not found");
                    return ResponseEntity.status(404).body(response);
                });
    }

    @PostMapping
    public ResponseEntity<ReqRes> createFavoriteSong(@RequestBody FavoriteSongDTO favoriteSongDTO) {
        FavoriteSong savedFavoriteSong = favoriteSongService.createFavori(favoriteSongDTO);

        FavoriteSongDTO responseDTO = new FavoriteSongDTO();
        responseDTO.setUserId(savedFavoriteSong.getUser().getUser_id());
        responseDTO.setSongId(savedFavoriteSong.getSong().getSongId());
        responseDTO.setMarkedAt(savedFavoriteSong.getMarkedAt());

        ReqRes response = new ReqRes();
        response.setStatusCode(201);
        response.setMessage("Favorite song created successfully");
        response.setFavoriteSongList(List.of(responseDTO));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }




    @DeleteMapping("/delete/{userId}/{songId}")
    public ResponseEntity<String> deleteFavoriteSong(
            @PathVariable int userId,
            @PathVariable int songId) {

        favoriteSongService.deleteFavoriteSong(userId, songId);
        return ResponseEntity.ok("Favorite song deleted successfully");
    }
}
