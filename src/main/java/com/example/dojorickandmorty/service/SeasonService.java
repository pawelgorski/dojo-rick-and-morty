package com.example.dojorickandmorty.service;

import com.example.dojorickandmorty.model.*;
import com.example.dojorickandmorty.model.Character;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class SeasonService {
    private final RestTemplate restTemplate = new RestTemplate();

    public List<SeasonStats> getNumberOfEpisodesInSeason(List<EpisodeLightDto> episodes) {
        if (episodes.isEmpty()) return null;

        Map<Integer, Integer> map = new TreeMap<>();

        for (EpisodeLightDto episode : episodes) {
            int season = Integer.parseInt(String.valueOf(episode.getEpisode().subSequence(1, 3)));
            if (map.containsKey(season)) {
                map.merge(season, 1, Integer::sum);
            } else {
                map.put(season, 1);
            }
        }
        List<SeasonStats> stats = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            SeasonStats stat = new SeasonStats(entry.getKey(), entry.getValue());
            stats.add(stat);
        }

        return stats;
    }

    public Season getEpisodesOfSeason(int id, List<EpisodeDto> episodes) {

        List<EpisodeFullDto> episodeDtos = new ArrayList<>();
        for (EpisodeDto episode : episodes) {
            int season = Integer.parseInt(String.valueOf(episode.getEpisode().subSequence(1, 3)));
            if (season == id) {
                List<Character> characters = new ArrayList<>();

                List<String> characterUrls = episode.getCharacters();

                for(String charUrl : characterUrls) {
                    ResponseEntity<Character> responseEntity =
                            restTemplate.getForEntity(charUrl, Character.class);
                    characters.add(responseEntity.getBody());
                }

                EpisodeFullDto episodeFullDto = EpisodeFullDto.builder()
                        .id(episode.getId())
                        .air_date(episode.getAir_date())
                        .characters(characters)
                        .name(episode.getName())
                        .episode(episode.getEpisode())
                        .build();

                episodeDtos.add(episodeFullDto);
            }
        }

        Season season = new Season(id,episodeDtos);
        return season;
    }
}
