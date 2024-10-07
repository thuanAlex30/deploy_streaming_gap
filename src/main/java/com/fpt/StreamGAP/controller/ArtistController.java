package com.fpt.StreamGAP.controller;

import com.fpt.StreamGAP.dto.ArtistDTO;
import com.fpt.StreamGAP.dto.ReqRes;
import com.fpt.StreamGAP.entity.Artist;
import com.fpt.StreamGAP.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @GetMapping
    public ReqRes getAllArtists() {
        List<Artist> artists = artistService.getAllArtists();

        List<ArtistDTO> artistDTOs = artists.stream()
                .map(artist -> {
                    ArtistDTO dto = new ArtistDTO();
                    dto.setArtist_id(artist.getArtist_id());
                    dto.setName(artist.getName());
                    dto.setCountry(artist.getBio());
                    dto.setDebut_date(artist.getCreated_at());
                    return dto;
                })
                .collect(Collectors.toList());

        ReqRes response = new ReqRes();
        response.setStatusCode(200);
        response.setMessage("Artists retrieved successfully");
        response.setArtistList(artistDTOs);
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReqRes> getArtistById(@PathVariable Integer id) {
        return artistService.getArtistById(id)
                .map(artist -> {
                    ArtistDTO dto = new ArtistDTO();
                    dto.setArtist_id(artist.getArtist_id());
                    dto.setName(artist.getName());
                    dto.setCountry(artist.getBio());
                    dto.setDebut_date(artist.getCreated_at());

                    ReqRes response = new ReqRes();
                    response.setStatusCode(200);
                    response.setMessage("Artist retrieved successfully");
                    response.setArtistList(List.of(dto));
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    ReqRes response = new ReqRes();
                    response.setStatusCode(404);
                    response.setMessage("Artist not found");
                    return ResponseEntity.status(404).body(response);
                });
    }

    @PostMapping
    public ReqRes createArtist(@RequestBody Artist artist) {
        Artist savedArtist = artistService.saveArtist(artist);

        ArtistDTO dto = new ArtistDTO();
        dto.setArtist_id(savedArtist.getArtist_id());
        dto.setName(savedArtist.getName());
        dto.setCountry(savedArtist.getBio());
        dto.setDebut_date(savedArtist.getCreated_at());

        ReqRes response = new ReqRes();
        response.setStatusCode(201);
        response.setMessage("Artist created successfully");
        response.setArtistList(List.of(dto));
        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReqRes> updateArtist(@PathVariable Integer id, @RequestBody Artist artist) {
        return artistService.getArtistById(id)
                .map(existingArtist -> {
                    artist.setArtist_id(id);
                    Artist updatedArtist = artistService.saveArtist(artist);

                    ArtistDTO dto = new ArtistDTO();
                    dto.setArtist_id(updatedArtist.getArtist_id());
                    dto.setName(updatedArtist.getName());
                    dto.setCountry(updatedArtist.getBio());
                    dto.setDebut_date(updatedArtist.getCreated_at());

                    ReqRes response = new ReqRes();
                    response.setStatusCode(200);
                    response.setMessage("Artist updated successfully");
                    response.setArtistList(List.of(dto));
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    ReqRes response = new ReqRes();
                    response.setStatusCode(404);
                    response.setMessage("Artist not found");
                    return ResponseEntity.status(404).body(response);
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReqRes> deleteArtist(@PathVariable Integer id) {
        if (artistService.getArtistById(id).isPresent()) {
            artistService.deleteArtist(id);
            ReqRes response = new ReqRes();
            response.setStatusCode(204);
            response.setMessage("Artist deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            ReqRes response = new ReqRes();
            response.setStatusCode(404);
            response.setMessage("Artist not found");
            return ResponseEntity.status(404).body(response);
        }
    }

}
