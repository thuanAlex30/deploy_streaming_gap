package com.fpt.StreamGAP.controller;

import com.fpt.StreamGAP.dto.PartyModeDTO;
import com.fpt.StreamGAP.entity.PartyMode;
import com.fpt.StreamGAP.service.PartyModeService;
import org.springframework.beans.factory.annotation.Autowired;
import com.fpt.StreamGAP.dto.ReqRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/partymodes")
public class PartyModeController {

    @Autowired
    private PartyModeService partyModeService;

    @GetMapping
    public ReqRes getAllPartyModes() {
        List<PartyMode> partyModes = partyModeService.getAllPartyModes();

        // Convert PartyMode entities to PartyModeDTOs
        List<PartyModeDTO> partyModeDTOs = partyModes.stream()
                .map(partyMode -> {
                    PartyModeDTO dto = new PartyModeDTO();
                    dto.setPartyId(partyMode.getParty_id());
                    dto.setPartyName(partyMode.getParty_name());
                    dto.setHostId(partyMode.getHost().getUser_id());
                    dto.setHostName(partyMode.getHost().getUsername());
                    dto.setCreatedAt(partyMode.getCreated_at().toString());
                    return dto;
                })
                .collect(Collectors.toList());

        ReqRes response = new ReqRes();
        response.setStatusCode(200);
        response.setMessage("Party modes retrieved successfully");
        response.setPartyModeList(partyModeDTOs);

        return response;
    }



}
