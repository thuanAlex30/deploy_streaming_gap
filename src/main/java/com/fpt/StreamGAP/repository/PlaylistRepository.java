package com.fpt.StreamGAP.repository;

import com.fpt.StreamGAP.entity.Playlist;
import com.fpt.StreamGAP.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Integer> {
    List<Playlist> findByUserUsername(String username);
    Optional<Playlist> findByPlaylistIdAndUserUsername(Integer playlistId, String username);
    Optional<Playlist> findByTitleAndUser( String title, User user);

}
