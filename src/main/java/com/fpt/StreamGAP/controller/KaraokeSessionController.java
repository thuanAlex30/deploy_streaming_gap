package com.fpt.StreamGAP.controller;

import com.fpt.StreamGAP.dto.KaraokeSessionDTO;
import com.fpt.StreamGAP.dto.ReqRes;
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
    public ResponseEntity<ReqRes> getAllKaraokeSessions() {
        List<KaraokeSessionDTO> sessions = karaokeSessionService.getAllKaraokeSessions();

        ReqRes response = new ReqRes();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Karaoke sessions retrieved successfully.");
        response.setKaraokeSessionList(sessions);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ReqRes> createKaraokeSession(@RequestBody KaraokeSessionDTO karaokeSessionDTO) {
        // Kiểm tra xem người dùng có tồn tại không
        Optional<User> optionalUser = Optional.ofNullable(karaokeSessionService.getUserById(karaokeSessionDTO.getUserId()));
        if (optionalUser.isEmpty()) {
            ReqRes response = new ReqRes();
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("User not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        // Kiểm tra xem bài hát có tồn tại không
        Optional<Song> optionalSong = Optional.ofNullable(karaokeSessionService.getSongById(karaokeSessionDTO.getSongId()));
        if (optionalSong.isEmpty()) {
            ReqRes response = new ReqRes();
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("Song not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        // Tạo session karaoke sau khi xác nhận user và song tồn tại
        KaraokeSessionDTO createdSession = karaokeSessionService.createKaraokeSession(
                karaokeSessionDTO, optionalUser.get(), optionalSong.get());

        ReqRes response = new ReqRes();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Karaoke session created successfully.");
        response.setKaraokeSessionList(List.of(createdSession));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @DeleteMapping("/{sessionId}")
    public ResponseEntity<ReqRes> deleteKaraokeSession(@PathVariable Integer sessionId) {
        Optional<KaraokeSessionDTO> sessionOptional = karaokeSessionService.getKaraokeSessionById(sessionId);

        ReqRes response = new ReqRes();
        if (sessionOptional.isPresent()) {
            karaokeSessionService.deleteKaraokeSession(sessionId);

            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Karaoke session deleted successfully.");
            response.setKaraokeSessionList(List.of(sessionOptional.get()));

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("Karaoke session not found.");

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/{sessionId}")
    public ResponseEntity<ReqRes> getKaraokeSessionById(@PathVariable Integer sessionId) {
        Optional<KaraokeSessionDTO> session = karaokeSessionService.getKaraokeSessionById(sessionId);

        ReqRes response = new ReqRes();
        if (session.isPresent()) {
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Karaoke session retrieved successfully.");
            response.setKaraokeSessionList(List.of(session.get()));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("Karaoke session not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
