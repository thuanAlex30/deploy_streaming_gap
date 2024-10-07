package com.fpt.StreamGAP.service;

import com.fpt.StreamGAP.entity.FavoriteSong;
import com.fpt.StreamGAP.entity.FavoriteSongId;
import com.fpt.StreamGAP.repository.FavoriteSongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteSongService {

    @Autowired
    private FavoriteSongRepository favoriteSongRepository;

    public List<FavoriteSong> getAllFavoriteSongs() {
        return favoriteSongRepository.findAll();
    }

    public Optional<FavoriteSong> getFavoriteSongById(FavoriteSongId id) {
        return favoriteSongRepository.findById(id);
    }

    public FavoriteSong saveFavoriteSong(FavoriteSong favoriteSong) {
        return favoriteSongRepository.save(favoriteSong);
    }

    public void deleteFavoriteSong(FavoriteSongId id) {
        favoriteSongRepository.deleteById(id);
    }


}
