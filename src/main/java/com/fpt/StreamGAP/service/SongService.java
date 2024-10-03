package com.fpt.StreamGAP.service;

import com.fpt.StreamGAP.dto.ReqRes;
import com.fpt.StreamGAP.entity.Album;
import com.fpt.StreamGAP.entity.Song;
import com.fpt.StreamGAP.repository.AlbumRepository;
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

    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    public Song getSongById(Integer songId) {
        return songRepository.findById(songId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Song not found"));
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
        existingSong.setTitle(songDetails.getTitle());
        existingSong.setGenre(songDetails.getGenre());
        existingSong.setDuration(songDetails.getDuration());
        existingSong.setAudio_file_url(songDetails.getAudio_file_url());
        existingSong.setLyrics(songDetails.getLyrics());
        existingSong.setCreated_at(songDetails.getCreated_at());
        return songRepository.save(existingSong);
    }

    public void deleteSong(Integer songId) {
        Song existingSong = getSongById(songId);
        songRepository.delete(existingSong);
    }
}
