package com.fpt.StreamGAP.repository;

import com.fpt.StreamGAP.entity.PlaylistSong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistSongRepository extends JpaRepository<PlaylistSong, Integer> {
}
