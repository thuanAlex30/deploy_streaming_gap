package com.fpt.StreamGAP.controller;

import com.fpt.StreamGAP.dto.PartyModeDTO;
import com.fpt.StreamGAP.entity.PartyMode;
import com.fpt.StreamGAP.service.PartyModeService;
import org.springframework.beans.factory.annotation.Autowired;
import com.fpt.StreamGAP.dto.ReqRes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Date;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/partymodes")
public class PartyModeController {

    @Autowired
    private PartyModeService partyModeService;

    @GetMapping
    public ReqRes getAllPartyModes() {
        List<PartyMode> partyModes = partyModeService.getAllPartyModes();

        List<PartyModeDTO> partyModeDTOs = partyModes.stream()
                .map(partyMode -> {
                    PartyModeDTO dto = new PartyModeDTO();
                    dto.setParty_Id(partyMode.getParty_id());
                    dto.setParty_Name(partyMode.getParty_name());
                    dto.setHost_Id(partyMode.getHost() != null ? partyMode.getHost().getUser_id() : null);
                    dto.setCreated_at(partyMode.getCreated_at());
                    return dto;
                })
                .collect(Collectors.toList());

        ReqRes response = new ReqRes();
        response.setStatusCode(200);
        response.setMessage("Party modes retrieved successfully");
        response.setPartyModeList(partyModeDTOs);

        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReqRes> getPartyModeById(@PathVariable Integer id) {
        PartyMode partyMode = partyModeService.getPartyModeById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy PartyMode với ID: " + id));

        PartyModeDTO dto = new PartyModeDTO();
        dto.setParty_Id(partyMode.getParty_id());
        dto.setParty_Name(partyMode.getParty_name());
        dto.setHost_Id(partyMode.getHost() != null ? partyMode.getHost().getUser_id() : null);
        dto.setCreated_at(partyMode.getCreated_at());

        ReqRes response = new ReqRes();
        response.setStatusCode(200);
        response.setMessage("Party mode retrieved successfully");
        response.setPartyModeList(List.of(dto));

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ReqRes> createPartyMode(@RequestBody PartyMode partyMode) {

        if (partyMode.getHost() == null || partyMode.getHost().getUser_id() == null) {
            throw new RuntimeException("Host must be provided for PartyMode.");
        }


        partyMode.setCreated_at(new Date(System.currentTimeMillis()));

        PartyMode createdPartyMode = partyModeService.savePartyMode(partyMode);

        PartyModeDTO dto = new PartyModeDTO();
        dto.setParty_Id(createdPartyMode.getParty_id());
        dto.setParty_Name(createdPartyMode.getParty_name());
        dto.setHost_Id(createdPartyMode.getHost().getUser_id());
        dto.setCreated_at(createdPartyMode.getCreated_at());

        ReqRes response = new ReqRes();
        response.setStatusCode(201);
        response.setMessage("Party mode created successfully");
        response.setPartyModeList(List.of(dto));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ReqRes> updatePartyMode(@PathVariable Integer id, @RequestBody PartyMode partyModeDetails) {
        return partyModeService.getPartyModeById(id)
                .map(existingPartyMode -> {

                    if (partyModeDetails.getParty_name() != null) {
                        existingPartyMode.setParty_name(partyModeDetails.getParty_name());
                    }

                    if (partyModeDetails.getHost() != null) {
                        existingPartyMode.setHost(partyModeDetails.getHost());
                    }

                    existingPartyMode.setCreated_at(new Date(System.currentTimeMillis()));
                    PartyMode updatedPartyMode = partyModeService.savePartyMode(existingPartyMode);


                    PartyModeDTO dto = new PartyModeDTO();
                    dto.setParty_Id(updatedPartyMode.getParty_id());
                    dto.setParty_Name(updatedPartyMode.getParty_name());
                    dto.setHost_Id(updatedPartyMode.getHost() != null ? updatedPartyMode.getHost().getUser_id() : null);
                    dto.setCreated_at(updatedPartyMode.getCreated_at());
                    ReqRes response = new ReqRes();
                    response.setStatusCode(200);
                    response.setMessage("Party mode updated successfully");
                    response.setPartyModeList(List.of(dto));

                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {

                    ReqRes response = new ReqRes();
                    response.setStatusCode(404);
                    response.setMessage("Party mode not found");
                    return ResponseEntity.status(404).body(response);
                });
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ReqRes> deletePartyMode(@PathVariable Integer id) {
        if (partyModeService.getPartyModeById(id).isPresent()) {
            partyModeService.deletePartyMode(id);
            ReqRes response = new ReqRes();
            response.setStatusCode(204);
            response.setMessage("Party mode deleted successfully");
            return ResponseEntity.noContent().build();
        } else {
            ReqRes response = new ReqRes();
            response.setStatusCode(404);
            response.setMessage("Party mode not found");
            return ResponseEntity.status(404).body(response);
        }
    }
}


