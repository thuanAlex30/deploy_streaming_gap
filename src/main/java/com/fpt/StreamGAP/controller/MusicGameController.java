package com.fpt.StreamGAP.controller;

import com.fpt.StreamGAP.dto.MusicGameDTO;
import com.fpt.StreamGAP.dto.ReqRes;
import com.fpt.StreamGAP.entity.MusicGame;
import com.fpt.StreamGAP.service.MusicGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/musicgames")
public class MusicGameController {

    @Autowired
    private MusicGameService musicGameService;

    @GetMapping
    public ReqRes getAllMusicGames() {
        ReqRes response = new ReqRes();
        try {
            List<MusicGameDTO> musicGames = musicGameService.getAllMusicGames();
            response.setStatusCode(200);
            response.setMessage("Success");
            response.setMusicGameList(musicGames);  // Use setMusicGameList
        } catch (Exception e) {
            response.setStatusCode(404);
            response.setMessage("Error occurred while fetching music games: " + e.getMessage());
        }
        return response;
    }

    @GetMapping("/{id}")
    public ReqRes getMusicGameById(@PathVariable Integer id) {
        ReqRes response = new ReqRes();
        Optional<MusicGameDTO> musicGame = musicGameService.getMusicGameById(id);
        if (musicGame.isPresent()) {
            response.setStatusCode(200);
            response.setMessage("Success");

            // Create a list with a single item and set it
            List<MusicGameDTO> musicGameList = Collections.singletonList(musicGame.get());
            response.setMusicGameList(musicGameList);  // Use setMusicGameList
        } else {
            response.setStatusCode(404);
            response.setMessage("Music game not found with ID: " + id);
        }
        return response;
    }

    @PostMapping
    public ReqRes createMusicGame(@RequestBody MusicGame musicGame) {
        ReqRes response = new ReqRes();
        try {
            MusicGame savedGame = musicGameService.saveMusicGame(musicGame);
            response.setStatusCode(201);
            response.setMessage("Music game created successfully");

            // Convert to DTO and set it as a single item list
            List<MusicGameDTO> musicGameList = Collections.singletonList(musicGameService.convertToDTO(savedGame));
            response.setMusicGameList(musicGameList);  // Use setMusicGameList
        } catch (Exception e) {
            response.setStatusCode(404);
            response.setMessage("Error occurred while creating music game: " + e.getMessage());
        }
        return response;
    }

    @PutMapping("/{id}")
    public ReqRes updateMusicGame(@PathVariable Integer id, @RequestBody MusicGame musicGame) {
        ReqRes response = new ReqRes();
        Optional<MusicGameDTO> existingGame = musicGameService.getMusicGameById(id);
        if (existingGame.isPresent()) {
            try {
                musicGame.setGame_id(id);
                MusicGame updatedGame = musicGameService.saveMusicGame(musicGame);
                response.setStatusCode(200);
                response.setMessage("Music game updated successfully");

                List<MusicGameDTO> musicGameList = Collections.singletonList(musicGameService.convertToDTO(updatedGame));
                response.setMusicGameList(musicGameList);  // Use setMusicGameList
            } catch (Exception e) {
                response.setStatusCode(404);
                response.setMessage("Error occurred while updating music game: " + e.getMessage());
            }
        } else {
            response.setStatusCode(404);
            response.setMessage("Music game not found with ID: " + id);
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ReqRes deleteMusicGame(@PathVariable Integer id) {
        ReqRes response = new ReqRes();
        if (musicGameService.getMusicGameById(id).isPresent()) {
            try {
                musicGameService.deleteMusicGame(id);
                response.setStatusCode(200);
                response.setMessage("Music game deleted successfully");
            } catch (Exception e) {
                response.setStatusCode(404);
                response.setMessage("Error occurred while deleting music game: " + e.getMessage());
            }
        } else {
            response.setStatusCode(404);
            response.setMessage("Music game not found with ID: " + id);
        }
        return response;
    }
}


