package com.fpt.StreamGAP.service;

import com.fpt.StreamGAP.entity.PartySong;
import com.fpt.StreamGAP.entity.PartySongId;
import com.fpt.StreamGAP.repository.PartySongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartySongService {

    @Autowired
    private PartySongRepository partySongRepository;

    public List<PartySong> getAllPartySongs() {
        return partySongRepository.findAll();
    }

    public Optional<PartySong> getPartySongById(PartySongId id) {
        return partySongRepository.findById(id);
    }

    public PartySong savePartySong(PartySong partySong) {
        return partySongRepository.save(partySong);
    }

    public void deletePartySong(PartySongId id) {
        partySongRepository.deleteById(id);
    }
}
