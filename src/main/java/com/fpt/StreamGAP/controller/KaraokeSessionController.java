package com.fpt.StreamGAP.controller;

import com.fpt.StreamGAP.dto.KaraokeSessionDTO;
import com.fpt.StreamGAP.entity.KaraokeSession;
import com.fpt.StreamGAP.entity.Song;
import com.fpt.StreamGAP.entity.User;
import com.fpt.StreamGAP.service.KaraokeSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/karaoke-sessions")
public class KaraokeSessionController {

    @Autowired
    private KaraokeSessionService karaokeSessionService;

    @GetMapping
    public ResponseEntity<List<KaraokeSessionDTO>> getAllKaraokeSessions() {
        List<KaraokeSessionDTO> sessions = karaokeSessionService.getAllKaraokeSessions();
        return new ResponseEntity<>(sessions, HttpStatus.OK);
    }

}
