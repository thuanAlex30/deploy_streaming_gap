package com.fpt.StreamGAP.controller;

import com.fpt.StreamGAP.entity.Artist;
import com.fpt.StreamGAP.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    // Get all artists
    @GetMapping
    public List<Artist> getAllArtists() {
        return artistService.getAllArtists();
    }

    // Get artist by ID
    @GetMapping("/{id}")
    public ResponseEntity<Artist> getArtistById(@PathVariable Integer id) {
        Optional<Artist> artist = artistService.getArtistById(id);
        return artist.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new artist
    @PostMapping
    public Artist createArtist(@RequestBody Artist artist) {
        System.out.println(artist);

        return artistService.saveArtist(artist);
    }

    // Update an existing artist
    @PutMapping("/{id}")
    public ResponseEntity<Artist> updateArtist(@PathVariable Integer id, @RequestBody Artist artistDetails) {
        Optional<Artist> artist = artistService.getArtistById(id);
        if (artist.isPresent()) {
            Artist existingArtist = artist.get();
            existingArtist.setName(artistDetails.getName());
            existingArtist.setBio(artistDetails.getBio());
            existingArtist.setProfile_image_url(artistDetails.getProfile_image_url());
            existingArtist.setCreated_at(artistDetails.getCreated_at());
            Artist updatedArtist = artistService.saveArtist(existingArtist);
            return ResponseEntity.ok(updatedArtist);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete an artist
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable Integer id) {
        artistService.deleteArtist(id);
        return ResponseEntity.noContent().build();
    }
}
