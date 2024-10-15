package com.fpt.StreamGAP.service;

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

    public void playSong(Integer id) {
        Song song = getSongById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Song not found"));

        List<SongListenStats> statsList = songListenStatsRepository.findBySong(song);
        SongListenStats stats;

        if (statsList.isEmpty()) {
            stats = new SongListenStats();
            stats.setSong(song);
            stats.setListen_count(1);
        } else {
            stats = statsList.get(0);
            stats.setListen_count(stats.getListen_count() + 1);
        }

        songListenStatsRepository.save(stats);
    }

    public Optional<Song> getSongById(Integer id) {
        return songRepository.findById(id);
    }

    public Song createSong(Song song) {
        try {
            return songRepository.save(song);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data integrity violation", e);
        }
    }

    public Song updateSong(Integer songId, Song songDetails) {
        if (songDetails == null) {
            throw new IllegalArgumentException("Song details cannot be null");
        }

        Song existingSong = getSongById(songId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Song not found"));

        existingSong.setTitle(songDetails.getTitle() != null ? songDetails.getTitle() : existingSong.getTitle());
        existingSong.setGenre(songDetails.getGenre() != null ? songDetails.getGenre() : existingSong.getGenre());
        existingSong.setDuration(songDetails.getDuration() != null ? songDetails.getDuration() : existingSong.getDuration());
        existingSong.setAudio_file_url(songDetails.getAudio_file_url() != null ? songDetails.getAudio_file_url() : existingSong.getAudio_file_url());
        existingSong.setLyrics(songDetails.getLyrics() != null ? songDetails.getLyrics() : existingSong.getLyrics());
        existingSong.setCreated_at(songDetails.getCreated_at() != null ? songDetails.getCreated_at() : existingSong.getCreated_at());

        if (songDetails.getAlbum() != null) {
            Album album = albumRepository.findById(songDetails.getAlbum().getAlbum_id())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Album not found"));
            existingSong.setAlbum(album);
        }

        return songRepository.save(existingSong);
    }

    public void deleteSong(Integer songId) {
        Song existingSong = getSongById(songId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Song not found"));
        songRepository.delete(existingSong);
    }
}
