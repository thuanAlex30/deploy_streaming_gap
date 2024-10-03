package com.fpt.StreamGAP.controller;

import com.fpt.StreamGAP.dto.ReqRes;
import com.fpt.StreamGAP.entity.Artist;
import com.fpt.StreamGAP.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @GetMapping
    public List<Artist> getAllArtists() {
        return artistService.getAllArtists();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artist> getArtistById(@PathVariable Integer id) {
        return ResponseEntity.ok(artistService.getArtistById(id));
    }

    @PostMapping
    public ResponseEntity<Artist> createArtist(@RequestBody Artist artist) {
        Artist newArtist = artistService.saveArtist(artist);
        return ResponseEntity.ok(newArtist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Artist> updateArtist(@PathVariable Integer id, @RequestBody Artist artistDetails) {
        Artist updatedArtist = artistService.updateArtist(id, artistDetails);
        return ResponseEntity.ok(updatedArtist);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable Integer id) {
        artistService.deleteArtist(id);
        return ResponseEntity.noContent().build();
    }
}