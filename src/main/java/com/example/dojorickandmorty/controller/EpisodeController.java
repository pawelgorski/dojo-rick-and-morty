package com.example.dojorickandmorty.controller;

import com.example.dojorickandmorty.service.EpisodeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EpisodeController {
    private final EpisodeService episodeService;

    @Autowired
    public EpisodeController(EpisodeService episodeService) {
        this.episodeService = episodeService;
    }

    @GetMapping("/episodes")
    public ResponseEntity<String> getEpisodesCounter() throws JsonProcessingException {
        String list = episodeService.getEpisodesCounter();
        return new ResponseEntity<String>(list, HttpStatus.OK);
    }
}
