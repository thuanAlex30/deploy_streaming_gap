package com.fpt.StreamGAP.repository;

import com.fpt.StreamGAP.entity.Playlist;
import com.fpt.StreamGAP.entity.PlaylistSong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistSongRepository extends JpaRepository<PlaylistSong, Integer> {


    List<PlaylistSong> findByCreatedByUsername(String username);
    Optional<PlaylistSong> findByIdAndCreatedByUsername(Integer id, String username);
}
