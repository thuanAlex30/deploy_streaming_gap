package com.fpt.StreamGAP.repository;

import com.fpt.StreamGAP.entity.PartySong;
import com.fpt.StreamGAP.entity.PartySongId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartySongRepository extends JpaRepository<PartySong, PartySongId> {

}
