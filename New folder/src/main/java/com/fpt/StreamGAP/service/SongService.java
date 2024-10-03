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

}
