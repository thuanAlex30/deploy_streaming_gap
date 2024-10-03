package com.fpt.StreamGAP.repository;

import com.fpt.StreamGAP.entity.KaraokeSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KaraokeSessionRepository extends JpaRepository<KaraokeSession, Integer> {

}
