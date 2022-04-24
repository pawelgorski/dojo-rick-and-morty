package com.example.dojorickandmorty.service;

import com.example.dojorickandmorty.model.*;
import com.example.dojorickandmorty.model.Character;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class SeasonService {
    public static final String CHARACTERS_URL = "https://rickandmortyapi.com/api/character/";
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
                StringBuilder characterNumbers = new StringBuilder();

                List<String> characterUrls = episode.getCharacters();

                for(String charUrl : characterUrls) {
                     int i = charUrl.length() -1;
                     while(charUrl.charAt(i) >= '0' && charUrl.charAt(i) <= '9') {
                         i--;
                     }
                     int characterNumber = Integer.parseInt(charUrl.substring(i+1));
                    characterNumbers.append(characterNumber);
                    characterNumbers.append(",");
                }
                characterNumbers.substring(0,characterNumbers.length() - 1);
                String getCharsUrl = CHARACTERS_URL + characterNumbers;


                ResponseEntity<Character[]> responseEntity =
                            restTemplate.getForEntity(getCharsUrl, Character[].class);
                characters.addAll(List.of(responseEntity.getBody()));

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
