package com.fpt.StreamGAP.controller;

import com.fpt.StreamGAP.dto.ReqRes;
import com.fpt.StreamGAP.entity.Album;
import com.fpt.StreamGAP.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/albums")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @GetMapping
    public List<Album> getAllAlbums() {
        return albumService.getAllAlbums();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Album> getAlbumById(@PathVariable Integer id) {
        return ResponseEntity.ok(albumService.getAlbumById(id));
    }
}
