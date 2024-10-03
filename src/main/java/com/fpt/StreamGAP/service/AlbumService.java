package com.fpt.StreamGAP.service;

import com.fpt.StreamGAP.entity.Album;
import com.fpt.StreamGAP.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    public Album getAlbumById(Integer id) {
        Optional<Album> album = albumRepository.findById(id);
        if (album.isPresent()) {
            return album.get();
        } else {
            throw new RuntimeException("Không tìm thấy album với ID: " + id);
        }
    }

}
