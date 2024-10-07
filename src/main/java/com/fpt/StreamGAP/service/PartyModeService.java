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
        // Validate that the host is present
        if (partyMode.getHost() == null || partyMode.getHost().getUser_id() == null) {
            throw new RuntimeException("Host must be provided for PartyMode.");
        }
        return partyModeRepository.save(partyMode);
    }

    public PartyMode updatePartyMode(Integer id, PartyMode partyModeDetails) {
        PartyMode partyMode = partyModeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Party Mode với ID: " + id));

        // Update properties from partyModeDetails
        if (partyModeDetails.getParty_name() != null) {
            partyMode.setParty_name(partyModeDetails.getParty_name());
        }
        if (partyModeDetails.getHost() != null) {
            partyMode.setHost(partyModeDetails.getHost());
        }
        if (partyModeDetails.getCreated_at() != null) {
            partyMode.setCreated_at(partyModeDetails.getCreated_at());
        }

        return partyModeRepository.save(partyMode);
    }

    public void deletePartyMode(Integer id) {
        // Check if the party mode exists before attempting to delete
        if (!partyModeRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy Party Mode với ID: " + id);
        }
        partyModeRepository.deleteById(id);
    }
}
