package com.fpt.StreamGAP.controller;

import com.fpt.StreamGAP.dto.KaraokeSessionDTO;
import com.fpt.StreamGAP.dto.ReqRes;
import com.fpt.StreamGAP.service.KaraokeSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/karaoke-sessions")
public class KaraokeSessionController {

    @Autowired
    private KaraokeSessionService karaokeSessionService;

    @GetMapping
    public ResponseEntity<ReqRes> getAllKaraokeSessions() {
        List<KaraokeSessionDTO> sessions = karaokeSessionService.getAllKaraokeSessions();

        ReqRes response = new ReqRes();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Karaoke sessions retrieved successfully.");
        response.setKaraokeSessionList(sessions);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
