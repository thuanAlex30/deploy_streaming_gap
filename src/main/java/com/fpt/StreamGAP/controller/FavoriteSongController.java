package com.fpt.StreamGAP.controller;

import com.fpt.StreamGAP.dto.FavoriteSongDTO;
import com.fpt.StreamGAP.dto.ReqRes;
import com.fpt.StreamGAP.service.FavoriteSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorite-songs")
public class FavoriteSongController {

    @Autowired
    private FavoriteSongService favoriteSongService;

    @GetMapping
    public ResponseEntity<ReqRes> getAllFavoriteSongs() {
        List<FavoriteSongDTO> favoriteSongs = favoriteSongService.getAllFavoriteSongs();

        ReqRes response = new ReqRes();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Favorite songs retrieved successfully.");
        response.setFavoriteSongList(favoriteSongs);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
