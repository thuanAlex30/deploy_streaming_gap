package com.fpt.StreamGAP.repository;

import com.fpt.StreamGAP.entity.PartyMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartyModeRepository extends JpaRepository<PartyMode, Integer> {
    List<PartyMode> findByHostUsername(String username);
    Optional<PartyMode> findByPartyIdAndHostUsername(Integer partyId, String username);
}