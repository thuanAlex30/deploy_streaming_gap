package com.fpt.StreamGAP.controller;

import com.fpt.StreamGAP.entity.SongListenStats;
import com.fpt.StreamGAP.service.SongListenStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/songsStats")
public class SongListenStatsController {

    @Autowired
    private SongListenStatsService service;

    @GetMapping
    public List<SongListenStats> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Optional<SongListenStats> getById(@PathVariable("id") Integer listenId) {
        return service.getById(listenId);
    }
}
