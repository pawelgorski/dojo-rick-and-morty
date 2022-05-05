package com.example.dojorickandmorty.controller;

import com.example.dojorickandmorty.exception.SeasonNotFoundException;
import com.example.dojorickandmorty.model.Episode;
import com.example.dojorickandmorty.model.Season;
import com.example.dojorickandmorty.model.dto.SeasonStatsDto;
import com.example.dojorickandmorty.service.EpisodeService;
import com.example.dojorickandmorty.service.SeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin()
public class SeasonController {
    private final EpisodeService episodeService;
    private final SeasonService seasonService;

    @Autowired
    public SeasonController(EpisodeService episodeService, SeasonService seasonService) {
        this.episodeService = episodeService;
        this.seasonService = seasonService;
    }


    @GetMapping("/seasons")
    public ResponseEntity<List<SeasonStatsDto>> getSeasonStats() {
        List<Episode> listOfEpisodes = episodeService.getAllEpisodes();
        List<SeasonStatsDto> result = seasonService.getNumberOfEpisodesInSeason(listOfEpisodes);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/seasons/{id}")
    public ResponseEntity<Season> getEpisodesOfSeason(@PathVariable int id) {
        List<Episode> listOfEpisodes = episodeService.getAllEpisodes();
        int greatestSeasonNumber = seasonService.getGreatestSeasonNumber(listOfEpisodes);
        if (id > 0 && id <= greatestSeasonNumber) {
            Season result = seasonService.getEpisodesOfSeason(id, listOfEpisodes);
            return new ResponseEntity<>(result, HttpStatus.OK);

        }
        throw new SeasonNotFoundException(String.format("Season with id %s not found!", id));

    }


}
