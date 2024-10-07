//package com.fpt.StreamGAP.controller;
//
//import com.fpt.StreamGAP.dto.ReqRes;
//import com.fpt.StreamGAP.entity.Artist;
//import com.fpt.StreamGAP.service.ArtistService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/artists")
//public class ArtistController {
//
//    @Autowired
//    private ArtistService artistService;
//
//    @GetMapping
//    public ReqRes getAllArtists() {
//        List<Artist> artists = artistService.getAllArtists();
//        ReqRes response = new ReqRes();
//        response.setStatusCode(200);
//        response.setMessage("Artists retrieved successfully");
//        response.setArtistList(artists);  // Bạn cần định nghĩa thêm trong `ReqRes`
//        return response;
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<ReqRes> getArtistById(@PathVariable Integer id) {
//        return artistService.getArtistById(id)
//                .map(artist -> {
//                    ReqRes response = new ReqRes();
//                    response.setStatusCode(200);
//                    response.setMessage("Artist retrieved successfully");
//                    response.setArtistList(List.of(artist));
//                    return ResponseEntity.ok(response);
//                })
//                .orElseGet(() -> {
//                    ReqRes response = new ReqRes();
//                    response.setStatusCode(404);
//                    response.setMessage("Artist not found");
//                    return ResponseEntity.status(404).body(response);
//                });
//    }
//
//    @PostMapping
//    public ReqRes createArtist(@RequestBody Artist artist) {
//        Artist savedArtist = artistService.saveArtist(artist);
//        ReqRes response = new ReqRes();
//        response.setStatusCode(201);
//        response.setMessage("Artist created successfully");
//        response.setArtistList(List.of(savedArtist));
//        return response;
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<ReqRes> updateArtist(@PathVariable Integer id, @RequestBody Artist artist) {
//        return artistService.getArtistById(id)
//                .map(existingArtist -> {
//                    artist.setArtist_id(id);
//                    Artist updatedArtist = artistService.saveArtist(artist);
//
//                    ReqRes response = new ReqRes();
//                    response.setStatusCode(200);
//                    response.setMessage("Artist updated successfully");
//                    response.setArtistList(List.of(updatedArtist));
//                    return ResponseEntity.ok(response);
//                })
//                .orElseGet(() -> {
//                    ReqRes response = new ReqRes();
//                    response.setStatusCode(404);
//                    response.setMessage("Artist not found");
//                    return ResponseEntity.status(404).body(response);
//                });
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<ReqRes> deleteArtist(@PathVariable Integer id) {
//        if (artistService.getArtistById(id).isPresent()) {
//            artistService.deleteArtist(id);
//            ReqRes response = new ReqRes();
//            response.setStatusCode(204);
//            response.setMessage("Artist deleted successfully");
//            return ResponseEntity.noContent().build();
//        } else {
//            ReqRes response = new ReqRes();
//            response.setStatusCode(404);
//            response.setMessage("Artist not found");
//            return ResponseEntity.status(404).body(response);
//        }
//    }
//
//}
