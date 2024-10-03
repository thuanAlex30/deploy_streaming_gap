package com.fpt.StreamGAP.controller;

import com.fpt.StreamGAP.dto.PartySongDTO;
import com.fpt.StreamGAP.dto.ReqRes;
import com.fpt.StreamGAP.entity.PartySong;
import com.fpt.StreamGAP.entity.PartySongId;
import com.fpt.StreamGAP.service.PartySongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/partysongs")
public class PartySongController {

    @Autowired
    private PartySongService partySongService;

    @GetMapping
    public ReqRes getAllPartySongs() {
        List<PartySong> partySongs = partySongService.getAllPartySongs();

        List<PartySongDTO> partySongDTOs = partySongs.stream()
                .map(partySong -> {
                    PartySongDTO dto = new PartySongDTO();
                    dto.setId(partySong.getId());
                    dto.setPartyId(partySong.getId().getParty_id());
                    dto.setSongId(partySong.getId().getSong_id());
                    dto.setSong(partySong.getSong());
                    return dto;
                })
                .collect(Collectors.toList());

        ReqRes response = new ReqRes();
        response.setStatusCode(200);
        response.setMessage("Party songs retrieved successfully");
        response.setPartySongList(partySongDTOs);
        return response;
    }


    @GetMapping("/{partyId}/{songId}")
    public ResponseEntity<PartySong> getPartySongById(@PathVariable Integer partyId, @PathVariable Integer songId) {
        PartySongId id = new PartySongId();
        id.setParty_id(partyId);
        id.setSong_id(songId);
        return partySongService.getPartySongById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public PartySong createPartySong(@RequestBody PartySong partySong) {
        return partySongService.savePartySong(partySong);
    }

    @PutMapping("/{partyId}/{songId}")
    public ResponseEntity<PartySong> updatePartySong(@PathVariable Integer partyId, @PathVariable Integer songId, @RequestBody PartySong partySong) {
        PartySongId id = new PartySongId();
        id.setParty_id(partyId);
        id.setSong_id(songId);
        return partySongService.getPartySongById(id)
                .map(existingPartySong -> {
                    partySong.setId(id);
                    return ResponseEntity.ok(partySongService.savePartySong(partySong));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{partyId}/{songId}")
    public ResponseEntity<Void> deletePartySong(@PathVariable Integer partyId, @PathVariable Integer songId) {
        PartySongId id = new PartySongId();
        id.setParty_id(partyId);
        id.setSong_id(songId);
        partySongService.deletePartySong(id);
        return ResponseEntity.noContent().build();
    }
}
