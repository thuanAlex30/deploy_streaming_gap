package com.fpt.StreamGAP.controller;

import com.fpt.StreamGAP.dto.FavoriteSongDTO;
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
    public ResponseEntity<List<FavoriteSongDTO>> getAllFavoriteSongs() {
        List<FavoriteSongDTO> favoriteSongs = favoriteSongService.getAllFavoriteSongs();
        return new ResponseEntity<>(favoriteSongs, HttpStatus.OK);
    }

}
