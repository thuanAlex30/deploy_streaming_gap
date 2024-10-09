package com.fpt.StreamGAP.service;

import com.fpt.StreamGAP.dto.ReqRes;
import com.fpt.StreamGAP.entity.Album;
import com.fpt.StreamGAP.entity.Song;
import com.fpt.StreamGAP.entity.SongListenStats;
import com.fpt.StreamGAP.repository.AlbumRepository;
import com.fpt.StreamGAP.repository.SongListenStatsRepository;
import com.fpt.StreamGAP.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class SongService {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private SongListenStatsRepository songListenStatsRepository;

    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    public Song getSongById(Integer id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Song not found"));

        SongListenStats stats = songListenStatsRepository.findBySong(song);
        if (stats == null) {
            stats = new SongListenStats();
            stats.setSong(song);
        }

        // Tăng số lượt nghe
        stats.setListen_count(stats.getListen_count() + 1);
        songListenStatsRepository.save(stats);

        return song;
    }


    public Song createSong(Song song) {
        try {
            return songRepository.save(song);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data integrity violation", e);
        }
    }

    public Song updateSong(Integer songId, Song songDetails) {
        Song existingSong = getSongById(songId);

        // Update song details
        existingSong.setTitle(songDetails.getTitle());
        existingSong.setGenre(songDetails.getGenre());
        existingSong.setDuration(songDetails.getDuration());
        existingSong.setAudio_file_url(songDetails.getAudio_file_url());
        existingSong.setLyrics(songDetails.getLyrics());
        existingSong.setCreated_at(songDetails.getCreated_at());

        // Check if the album exists before updating
        if (songDetails.getAlbum() != null) {
            // Assuming album has a method to fetch by ID
            Album album = albumRepository.findById(songDetails.getAlbum().getAlbum_id())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Album not found"));
            existingSong.setAlbum(album);
        }

        return songRepository.save(existingSong);
    }

    public void deleteSong(Integer songId) {
        Song existingSong = getSongById(songId);
        songRepository.delete(existingSong);
    }
}