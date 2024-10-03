package com.fpt.StreamGAP.repository;

import com.fpt.StreamGAP.entity.MusicGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicGameRepository extends JpaRepository<MusicGame, Integer> {

}
