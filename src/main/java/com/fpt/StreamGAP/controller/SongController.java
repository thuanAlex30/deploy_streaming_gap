package com.fpt.StreamGAP.controller;

import com.fpt.StreamGAP.dto.ReqRes;
import com.fpt.StreamGAP.dto.SongDTO;
import com.fpt.StreamGAP.dto.StatisticsDTO; // Import StatisticsDTO
import com.fpt.StreamGAP.entity.Song;
import com.fpt.StreamGAP.service.SongListenStatsService; // Import service
import com.fpt.StreamGAP.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/songs")
public class SongController {

    @Autowired
    private SongService songService;

    @Autowired
    private SongListenStatsService songListenStatsService; // Khai báo dịch vụ

    @GetMapping
    public ReqRes getAllSongs() {
        List<Song> songs = songService.getAllSongs();

        List<SongDTO> songDTOs = songs.stream()
                .map(song -> {
                    SongDTO dto = new SongDTO();
                    dto.setSong_id(song.getSong_id());
                    dto.setAlbum(song.getAlbum());
                    dto.setTitle(song.getTitle());
                    dto.setGenre(song.getGenre());
                    dto.setDuration(song.getDuration());
                    dto.setAudio_file_url(song.getAudio_file_url());
                    dto.setLyrics(song.getLyrics());
                    dto.setCreated_at(song.getCreated_at());

                    // Lấy số lượt nghe từ StatisticsDTO
                    StatisticsDTO stats = songListenStatsService.getStatsBySongId(song.getSong_id());
                    dto.setListen_count(stats != null ? (int) stats.getCount() : 0); // Chuyển đổi count thành int

                    return dto;
                })
                .collect(Collectors.toList());

        ReqRes response = new ReqRes();
        response.setStatusCode(200);
        response.setMessage("Songs retrieved successfully");
        response.setSongDtoList(songDTOs);

        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReqRes> getSongById(@PathVariable Integer id) {
        Song song = songService.getSongById(id);

        SongDTO dto = new SongDTO();
        dto.setSong_id(song.getSong_id());
        dto.setAlbum(song.getAlbum());
        dto.setTitle(song.getTitle());
        dto.setGenre(song.getGenre());
        dto.setDuration(song.getDuration());
        dto.setAudio_file_url(song.getAudio_file_url());
        dto.setLyrics(song.getLyrics());
        dto.setCreated_at(song.getCreated_at());

        // Lấy số lượt nghe từ StatisticsDTO
        StatisticsDTO stats = songListenStatsService.getStatsBySongId(song.getSong_id());
        dto.setListen_count(stats != null ? (int) stats.getCount() : 0); // Chuyển đổi count thành int

        ReqRes response = new ReqRes();
        response.setStatusCode(200);
        response.setMessage("Song retrieved successfully");
        response.setSongDtoList(List.of(dto));

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ReqRes> createSong(@RequestBody Song song) {
        Song createdSong = songService.createSong(song);
        SongDTO dto = new SongDTO();
        dto.setSong_id(createdSong.getSong_id());
        dto.setAlbum(createdSong.getAlbum());
        dto.setTitle(createdSong.getTitle());
        dto.setGenre(createdSong.getGenre());
        dto.setDuration(createdSong.getDuration());
        dto.setAudio_file_url(createdSong.getAudio_file_url());
        dto.setLyrics(createdSong.getLyrics());
        dto.setCreated_at(createdSong.getCreated_at());

        // Đặt số lượt nghe mặc định là 0 cho bài hát mới
        dto.setListen_count(0);

        ReqRes response = new ReqRes();
        response.setStatusCode(201);
        response.setMessage("Song created successfully");
        response.setSongDtoList(List.of(dto));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReqRes> updateSong(@PathVariable Integer id, @RequestBody Song songDetails) {
        Song existingSong = songService.getSongById(id);

        if (existingSong != null) {
            songDetails.setSong_id(id);
            Song updatedSong = songService.updateSong(id, songDetails);

            SongDTO dto = new SongDTO();
            dto.setSong_id(updatedSong.getSong_id());
            dto.setAlbum(updatedSong.getAlbum());
            dto.setTitle(updatedSong.getTitle());
            dto.setGenre(updatedSong.getGenre());
            dto.setDuration(updatedSong.getDuration());
            dto.setAudio_file_url(updatedSong.getAudio_file_url());
            dto.setLyrics(updatedSong.getLyrics());
            dto.setCreated_at(updatedSong.getCreated_at());

            // Lấy số lượt nghe từ StatisticsDTO
            StatisticsDTO stats = songListenStatsService.getStatsBySongId(updatedSong.getSong_id());
            dto.setListen_count(stats != null ? (int) stats.getCount() : 0); // Chuyển đổi count thành int

            ReqRes response = new ReqRes();
            response.setStatusCode(200);
            response.setMessage("Song updated successfully");
            response.setSongDtoList(List.of(dto));

            return ResponseEntity.ok(response);
        } else {
            ReqRes response = new ReqRes();
            response.setStatusCode(404);
            response.setMessage("Song not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReqRes> deleteSong(@PathVariable Integer id) {
        if (songService.getSongById(id) != null) {
            songService.deleteSong(id);
            ReqRes response = new ReqRes();
            response.setStatusCode(204);
            response.setMessage("Song deleted successfully");
            return ResponseEntity.noContent().build();
        } else {
            ReqRes response = new ReqRes();
            response.setStatusCode(404);
            response.setMessage("Song not found");
            return ResponseEntity.status(404).body(response);
        }
    }
}
