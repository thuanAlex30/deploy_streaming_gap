package com.fpt.StreamGAP.service;

import com.fpt.StreamGAP.entity.PartyMode;
import com.fpt.StreamGAP.repository.PartyModeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartyModeService {

    @Autowired
    private PartyModeRepository partyModeRepository;

    public List<PartyMode> getAllPartyModes() {
        return partyModeRepository.findAll();
    }

    public Optional<PartyMode> getPartyModeById(Integer id) {
        return partyModeRepository.findById(id);
    }

    public PartyMode savePartyMode(PartyMode partyMode) {
        return partyModeRepository.save(partyMode);
    }

    public void deletePartyMode(Integer id) {
        partyModeRepository.deleteById(id);
    }
}
