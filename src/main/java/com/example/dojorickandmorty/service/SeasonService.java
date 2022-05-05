package com.example.dojorickandmorty.service;

import com.example.dojorickandmorty.model.Character;
import com.example.dojorickandmorty.model.*;
import com.example.dojorickandmorty.model.dto.EpisodeFullDto;
import com.example.dojorickandmorty.model.dto.SeasonStatsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class SeasonService {
    public static final String CHARACTERS_URL = "https://rickandmortyapi.com/api/character/";
    private final RestTemplate restTemplate = new RestTemplate();

    public List<SeasonStatsDto> getNumberOfEpisodesInSeason(List<Episode> episodes) {
        if (episodes.isEmpty()) return null;
        Map<Integer, Integer> numOfEpisodesInSeason = getMapOfNumberOfEpisodesInSeason(episodes);
        return getListOfSeasonStats(numOfEpisodesInSeason);
    }

    private List<SeasonStatsDto> getListOfSeasonStats
            (Map<Integer, Integer> numOfEpisodesInSeason){

        List<SeasonStatsDto> stats = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : numOfEpisodesInSeason.entrySet()) {
            SeasonStatsDto stat = new SeasonStatsDto(entry.getKey(), entry.getValue());
            stats.add(stat);
        }
        return stats;
    }

    private Map<Integer,Integer> getMapOfNumberOfEpisodesInSeason(List<Episode> episodes){
        Map<Integer, Integer> numOfEpisodesInSeason = new TreeMap<>();

        for (Episode episode : episodes) {
            int season = getSeasonNumber(episode); // fixme this is the consequence of holding ints in strings
            if (numOfEpisodesInSeason.containsKey(season)) {
                numOfEpisodesInSeason.merge(season, 1, Integer::sum);
            } else {
                numOfEpisodesInSeason.put(season, 1);
            }
        }
        return numOfEpisodesInSeason;
    }

    public Season getEpisodesOfSeason(int seasonId, List<Episode> episodes) {
        List<EpisodeFullDto> episodeDtos = new ArrayList<>();
        for (Episode episode : episodes) {
            int season = getSeasonNumber(episode);
            if (season == seasonId) {

                List<Character> characters;

                String charsUrl = getCharactersUrl(episode);

                ResponseEntity<Character[]> responseEntity =
                        restTemplate.getForEntity(charsUrl, Character[].class);

                if(responseEntity.hasBody()) {
                    characters = new ArrayList<>(List.of(Objects
                            .requireNonNull(responseEntity.getBody())));
                } else {
                    throw new RuntimeException("Response body is null!");
                }

                EpisodeFullDto episodeFullDto = EpisodeFullDto.builder()
                        .id(episode.getId())
                        .air_date(episode.getAir_date())
                        .characters(characters)
                        .name(episode.getName())
                        .seasonAndEpisode(episode.getSeasonAndEpisode())
                        .build();

                episodeDtos.add(episodeFullDto);
            }
        }

        return new Season(seasonId, episodeDtos);
    }

    private String getCharactersUrl(Episode episode){
        StringBuilder characterNumbers = new StringBuilder();
        characterNumbers.append(CHARACTERS_URL);

        List<String> characterUrls = episode.getCharacterUrls();

        for (String charUrl : characterUrls) {
            int charNumber = Integer.parseInt(charUrl.substring(charUrl.lastIndexOf('/') + 1));
            characterNumbers.append(charNumber);
            characterNumbers.append(",");
        }
        characterNumbers.deleteCharAt(characterNumbers.length()-1);

        return characterNumbers.toString();
    }

    private int getSeasonNumber(Episode episode) {
        return Integer.parseInt(String.valueOf(episode.getSeasonAndEpisode().subSequence(1, 3)));
    }

    public int getGreatestSeasonNumber(List<Episode> listOfEpisodes) {
        int greatestSeasonNumber = 1;
        for(Episode episode : listOfEpisodes) {
            greatestSeasonNumber = Math.max(greatestSeasonNumber,
                    getSeasonNumber(episode));
        }
        return greatestSeasonNumber;
    }
}
