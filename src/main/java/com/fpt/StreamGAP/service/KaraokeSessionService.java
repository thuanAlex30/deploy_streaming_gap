package com.fpt.StreamGAP.service;

import com.fpt.StreamGAP.dto.KaraokeSessionDTO;
import com.fpt.StreamGAP.entity.KaraokeSession;
import com.fpt.StreamGAP.entity.Song;
import com.fpt.StreamGAP.entity.User;
import com.fpt.StreamGAP.repository.KaraokeSessionRepository;
import com.fpt.StreamGAP.repository.SongRepository;
import com.fpt.StreamGAP.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class KaraokeSessionService {

    @Autowired
    private UserRepo userRepository;
    @Autowired
    private SongRepository songRepository;
    @Autowired
    private KaraokeSessionRepository karaokeSessionRepository;

    public User getUserById(Integer userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public Song getSongById(Integer songId) {
        return songRepository.findById(songId).orElse(null);
    }

    public KaraokeSessionDTO createKaraokeSession(KaraokeSessionDTO karaokeSessionDTO, User user, Song song) {
        KaraokeSession karaokeSession = new KaraokeSession();
        karaokeSession.setUser(user);
        karaokeSession.setSong(song);
        karaokeSession.setRecording_url(karaokeSessionDTO.getRecordingUrl());
        karaokeSession.setCreated_at(new Date(System.currentTimeMillis()));

        karaokeSession = karaokeSessionRepository.save(karaokeSession);
        return convertToDTO(karaokeSession);
    }

    public List<KaraokeSessionDTO> getAllKaraokeSessions() {
        List<KaraokeSession> sessions = karaokeSessionRepository.findAll();
        return sessions.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private KaraokeSessionDTO convertToDTO(KaraokeSession session) {
        KaraokeSessionDTO dto = new KaraokeSessionDTO();
        dto.setSessionId(session.getSession_id());
        dto.setUserId(session.getUser().getUser_id());
        dto.setSongId(session.getSong().getSong_id());
        dto.setRecordingUrl(session.getRecording_url());
        dto.setCreatedAt(session.getCreated_at());
        return dto;
    }

    public void deleteKaraokeSession(Integer sessionId) {
        karaokeSessionRepository.deleteById(sessionId);
    }

    public Optional<KaraokeSessionDTO> getKaraokeSessionById(Integer sessionId) {
        Optional<KaraokeSession> session = karaokeSessionRepository.findById(sessionId);
        return session.map(this::convertToDTO);
    }
}
