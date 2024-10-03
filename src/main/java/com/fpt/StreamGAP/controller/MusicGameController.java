package com.fpt.StreamGAP.controller;

import com.fpt.StreamGAP.dto.MusicGameDTO;
import com.fpt.StreamGAP.dto.ReqRes;
import com.fpt.StreamGAP.entity.MusicGame;
import com.fpt.StreamGAP.service.MusicGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/musicgames")
public class MusicGameController {

    @Autowired
    private MusicGameService musicGameService;

    @GetMapping
    public ReqRes getAllMusicGames() {
        List<MusicGameDTO> musicGames = musicGameService.getAllMusicGames();
        ReqRes response = new ReqRes();
        response.setStatusCode(200);
        response.setMessage("Success");
        response.setMusicGameList(musicGames);
        return response;
    }



}
