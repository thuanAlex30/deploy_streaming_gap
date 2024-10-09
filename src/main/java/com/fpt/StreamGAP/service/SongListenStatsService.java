package com.fpt.StreamGAP.service;

import com.fpt.StreamGAP.dto.StatisticsDTO;
import com.fpt.StreamGAP.entity.Song;
import com.fpt.StreamGAP.entity.SongListenStats;
import com.fpt.StreamGAP.repository.SongListenStatsRepository;
import com.fpt.StreamGAP.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SongListenStatsService {

    @Autowired
    private SongListenStatsRepository repository;
    @Autowired
    private SongListenStatsRepository songListenStatsRepository;

    @Autowired
    private SongRepository songRepository;
    public List<SongListenStats> getAll() {
        return repository.findAll();
    }

    public Optional<SongListenStats> getById(Integer listenId) {
        return repository.findById(listenId);
    }

    public SongListenStats recordListen(Integer songId) {
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Song not found"));

        SongListenStats stats = songListenStatsRepository.findBySong(song);
        if (stats == null) {
            stats = new SongListenStats();
            stats.setSong(song);
            stats.setListen_count(1); // Thiết lập lượt nghe ban đầu
            stats.setRecorded_at(new Date(System.currentTimeMillis())); // Thiết lập thời gian ghi nhận
        } else {
            stats.setListen_count(stats.getListen_count() + 1);
        }

        return songListenStatsRepository.save(stats);
    }

    public StatisticsDTO getStatsBySongId(Integer songId) {
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Song not found"));

        SongListenStats stats = songListenStatsRepository.findBySong(song);
        StatisticsDTO dto = new StatisticsDTO();

        if (stats != null) {
            dto.setLabel("Listen count");
            dto.setCount(stats.getListen_count());
        } else {
            dto.setLabel("Listen count");
            dto.setCount(0); // Nếu không có lượt nghe, đặt giá trị mặc định là 0
        }

        return dto;
    }
}
