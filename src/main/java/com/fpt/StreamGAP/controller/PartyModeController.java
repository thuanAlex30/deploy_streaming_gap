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
        response.setPartyModeList(partyModeDTOs); // Assuming ReqRes has this method

        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReqRes> getPartyModeById(@PathVariable Integer id) {
        PartyMode partyMode = partyModeService.getPartyModeById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy PartyMode với ID: " + id));

        // Convert to DTO
        PartyModeDTO dto = new PartyModeDTO();
        dto.setPartyId(partyMode.getParty_id());
        dto.setPartyName(partyMode.getParty_name());
        dto.setHostId(partyMode.getHost().getUser_id());
        dto.setHostName(partyMode.getHost().getUsername());
        dto.setCreatedAt(partyMode.getCreated_at().toString());

        ReqRes response = new ReqRes();
        response.setStatusCode(200);
        response.setMessage("Party mode retrieved successfully");
        response.setPartyModeList(List.of(dto)); // Wrap DTO in a list

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ReqRes> createPartyMode(@RequestBody PartyMode partyMode) {
        PartyMode createdPartyMode = partyModeService.savePartyMode(partyMode);

        // Convert to DTO
        PartyModeDTO dto = new PartyModeDTO();
        dto.setPartyId(createdPartyMode.getParty_id());
        dto.setPartyName(createdPartyMode.getParty_name());
        dto.setHostId(createdPartyMode.getHost().getUser_id());
        dto.setHostName(createdPartyMode.getHost().getUsername());
        dto.setCreatedAt(createdPartyMode.getCreated_at().toString());

        ReqRes response = new ReqRes();
        response.setStatusCode(201);
        response.setMessage("Party mode created successfully");
        response.setPartyModeList(List.of(dto)); // Wrap DTO in a list

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReqRes> updatePartyMode(@PathVariable Integer id, @RequestBody PartyMode partyModeDetails) {
        return partyModeService.getPartyModeById(id)
                .map(existingPartyMode -> {

                    partyModeDetails.setParty_id(id);
                    PartyMode updatedPartyMode = partyModeService.savePartyMode(partyModeDetails);


                    PartyModeDTO dto = new PartyModeDTO();
                    dto.setPartyId(updatedPartyMode.getParty_id());
                    dto.setPartyName(updatedPartyMode.getParty_name());
                    dto.setHostId(updatedPartyMode.getHost().getUser_id());
                    dto.setHostName(updatedPartyMode.getHost().getUsername());
                    dto.setCreatedAt(updatedPartyMode.getCreated_at().toString());

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

