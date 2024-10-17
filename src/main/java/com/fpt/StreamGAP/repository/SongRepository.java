package com.fpt.StreamGAP.repository;

import com.fpt.StreamGAP.entity.PlaylistSong;
import com.fpt.StreamGAP.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<Song, Integer> {
    List<Song> findByCreatedByUsername(String username);
    Optional<Song> findBySongIdAndCreatedByUsername(Integer songId, String username);
}