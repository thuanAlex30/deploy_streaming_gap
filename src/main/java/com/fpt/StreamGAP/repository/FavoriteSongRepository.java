package com.fpt.StreamGAP.repository;

import com.fpt.StreamGAP.entity.FavoriteSong;
import com.fpt.StreamGAP.entity.FavoriteSongId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteSongRepository extends JpaRepository<FavoriteSong, FavoriteSongId> {
}
