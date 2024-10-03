package com.fpt.StreamGAP.service;

import com.fpt.StreamGAP.entity.Artist;
import com.fpt.StreamGAP.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    public Artist getArtistById(Integer id) {
        Optional<Artist> artist = artistRepository.findById(id);
        if (artist.isPresent()) {
            return artist.get();
        } else {
            throw new RuntimeException("Không tìm thấy nghệ sĩ với ID: " + id);
        }
    }

    public Artist saveArtist(Artist artist) {
        return artistRepository.save(artist);
    }

    public Artist updateArtist(Integer id, Artist artistDetails) {
        // Kiểm tra xem nghệ sĩ có tồn tại hay không
        Artist artist = artistRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Không tìm thấy nghệ sĩ với ID: " + id));

        if (artistDetails.getName() != null) {
            artist.setName(artistDetails.getName());
        }
        if (artistDetails.getBio() != null) {
            artist.setBio(artistDetails.getBio());
        }
        if (artistDetails.getProfile_image_url() != null) {
            artist.setProfile_image_url(artistDetails.getProfile_image_url());
        }
        // Thêm các thuộc tính khác nếu cần...

        // Lưu lại nghệ sĩ đã cập nhật
        return artistRepository.save(artist);
    }

    public void deleteArtist(Integer id) {
        artistRepository.deleteById(id);
    }
}
