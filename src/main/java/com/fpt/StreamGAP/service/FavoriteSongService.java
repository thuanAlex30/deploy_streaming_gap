package com.fpt.StreamGAP.service;

import com.fpt.StreamGAP.dto.FavoriteSongDTO;
import com.fpt.StreamGAP.entity.FavoriteSong;
import com.fpt.StreamGAP.repository.FavoriteSongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavoriteSongService {

    @Autowired
    private FavoriteSongRepository favoriteSongRepository;

    private FavoriteSongDTO convertToDTO(FavoriteSong favoriteSong) {
        FavoriteSongDTO dto = new FavoriteSongDTO();
        dto.setUserId(favoriteSong.getId().getUserId());
        dto.setSongId(favoriteSong.getId().getSongId());
        dto.setMarkedAt(favoriteSong.getMarkedAt());
        return dto;
    }

    public List<FavoriteSongDTO> getAllFavoriteSongs() {
        List<FavoriteSong> favoriteSongs = favoriteSongRepository.findAll();
        return favoriteSongs.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


}
