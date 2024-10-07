package com.fpt.StreamGAP.controller;

import com.fpt.StreamGAP.dto.FavoriteSongDTO;
import com.fpt.StreamGAP.dto.ReqRes;
import com.fpt.StreamGAP.entity.FavoriteSong;
import com.fpt.StreamGAP.entity.FavoriteSongId;
import com.fpt.StreamGAP.service.FavoriteSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/favorite-songs")
public class FavoriteSongController {

    @Autowired
    private FavoriteSongService favoriteSongService;

    @GetMapping
    public ReqRes getAllFavoriteSongs() {
        List<FavoriteSong> favoriteSongs = favoriteSongService.getAllFavoriteSongs();

        List<FavoriteSongDTO> favoriteSongDTOs = favoriteSongs.stream()
                .map(favoriteSong -> {
                    FavoriteSongDTO dto = new FavoriteSongDTO();
                    dto.setUserId(favoriteSong.getId().getUserId());
                    dto.setSongId(favoriteSong.getId().getSongId());
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
        favoriteSongId.setUserId(userId); // Thiết lập userId
        favoriteSongId.setSongId(songId); // Thiết lập songId

        return favoriteSongService.getFavoriteSongById(favoriteSongId)
                .map(favoriteSong -> {
                    FavoriteSongDTO dto = new FavoriteSongDTO();
                    dto.setUserId(favoriteSong.getId().getUserId());
                    dto.setSongId(favoriteSong.getId().getSongId());
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
    public ReqRes createFavoriteSong(@RequestBody FavoriteSong favoriteSong) {
        FavoriteSong savedFavoriteSong = favoriteSongService.saveFavoriteSong(favoriteSong);

        FavoriteSongDTO dto = new FavoriteSongDTO();
        dto.setUserId(savedFavoriteSong.getId().getUserId());
        dto.setSongId(savedFavoriteSong.getId().getSongId());
        dto.setMarkedAt(savedFavoriteSong.getMarkedAt());

        ReqRes response = new ReqRes();
        response.setStatusCode(201);
        response.setMessage("Favorite song created successfully");
        response.setFavoriteSongList(List.of(dto));
        return response;
    }

    @PutMapping("/{userId}/{songId}")
    public ResponseEntity<ReqRes> updateFavoriteSong(@PathVariable Integer userId, @PathVariable Integer songId, @RequestBody FavoriteSong favoriteSong) {
        FavoriteSongId favoriteSongId = new FavoriteSongId();
        favoriteSongId.setUserId(userId); // Thiết lập userId
        favoriteSongId.setSongId(songId); // Thiết lập songId

        return favoriteSongService.getFavoriteSongById(favoriteSongId)
                .map(existingFavoriteSong -> {
                    favoriteSong.setId(favoriteSongId);
                    FavoriteSong updatedFavoriteSong = favoriteSongService.saveFavoriteSong(favoriteSong);

                    FavoriteSongDTO dto = new FavoriteSongDTO();
                    dto.setUserId(updatedFavoriteSong.getId().getUserId());
                    dto.setSongId(updatedFavoriteSong.getId().getSongId());
                    dto.setMarkedAt(updatedFavoriteSong.getMarkedAt());

                    ReqRes response = new ReqRes();
                    response.setStatusCode(200);
                    response.setMessage("Favorite song updated successfully");
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

    @DeleteMapping("/{userId}/{songId}")
    public ResponseEntity<ReqRes> deleteFavoriteSong(@PathVariable Integer userId, @PathVariable Integer songId) {
        FavoriteSongId favoriteSongId = new FavoriteSongId();
        favoriteSongId.setUserId(userId); // Thiết lập userId
        favoriteSongId.setSongId(songId); // Thiết lập songId

        if (favoriteSongService.getFavoriteSongById(favoriteSongId).isPresent()) {
            favoriteSongService.deleteFavoriteSong(favoriteSongId);
            ReqRes response = new ReqRes();
            response.setStatusCode(204);
            response.setMessage("Favorite song deleted successfully");
            return ResponseEntity.noContent().build();
        } else {
            ReqRes response = new ReqRes();
            response.setStatusCode(404);
            response.setMessage("Favorite song not found");
            return ResponseEntity.status(404).body(response);
        }
    }
}
