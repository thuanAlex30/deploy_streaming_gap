package com.fpt.StreamGAP.repository;

import com.fpt.StreamGAP.entity.FavoriteSong;
import com.fpt.StreamGAP.entity.FavoriteSongId;
import com.fpt.StreamGAP.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteSongRepository extends JpaRepository<FavoriteSong, FavoriteSongId> {
    List<FavoriteSong> findByUser(User user);
    void deleteById(FavoriteSongId favoriteSongId);
}
