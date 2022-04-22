package com.example.dojorickandmorty.controller;

import com.example.dojorickandmorty.model.EpisodeDto;
import com.example.dojorickandmorty.model.EpisodeLightDto;
import com.example.dojorickandmorty.model.Season;
import com.example.dojorickandmorty.model.SeasonStats;
import com.example.dojorickandmorty.service.EpisodeService;
import com.example.dojorickandmorty.service.SeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SeasonController {
    private final EpisodeService episodeService;
    private final SeasonService seasonService;

    @Autowired
    public SeasonController(EpisodeService episodeService, SeasonService seasonService) {
        this.episodeService = episodeService;
        this.seasonService = seasonService;
    }


    @GetMapping("/seasons")
    public ResponseEntity<List<SeasonStats>> getSeasonStats() {
        List<EpisodeLightDto> listOfEpisodes = episodeService.getAllEpisodes();
        List<SeasonStats> result = seasonService.getNumberOfEpisodesInSeason(listOfEpisodes);
        return new ResponseEntity<List<SeasonStats>>(result, HttpStatus.OK);
    }

    @GetMapping("/seasons/{id}")
    public ResponseEntity<Season> getEpisodesOfSeason(@PathVariable int id) {
        List<EpisodeDto> listOfEpisodes = episodeService.getAllEpisodesFull();
        Season result = seasonService.getEpisodesOfSeason(id, listOfEpisodes);
        return new ResponseEntity<Season>(result, HttpStatus.OK);
    }


}
