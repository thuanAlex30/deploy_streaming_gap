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
    public void playSong(Integer Id) {
        Song song = getSongById(Id);

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

    public Song getSongById(Integer id) {

        return songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Song not found"));
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

        if (songDetails.getAlbum() != null) {
            Album album = albumRepository.findById(songDetails.getAlbum().getAlbum_id())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Album not found"));
            existingSong.setAlbum(album);
        }

        return songRepository.save(existingSong);
    }

    public void deleteSong(Integer songId) {
        Song existingSong = getSongById(songId);
        if (existingSong != null) {
            songRepository.delete(existingSong);
        } else {
            throw new RuntimeException("Song not found");
        }
    }
}
