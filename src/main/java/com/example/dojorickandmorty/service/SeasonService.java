package com.example.dojorickandmorty.service;

import com.example.dojorickandmorty.model.Character;
import com.example.dojorickandmorty.model.*;
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

    public List<SeasonStats> getNumberOfEpisodesInSeason(List<EpisodeDto> episodes) {
        if (episodes.isEmpty()) return null;
        Map<Integer, Integer> numOfEpisodesInSeason = getMapOfNumberOfEpisodesInSeason(episodes);
        return getListOfSeasonStats(numOfEpisodesInSeason);
    }

    private List<SeasonStats> getListOfSeasonStats
            (Map<Integer, Integer> numOfEpisodesInSeason){

        List<SeasonStats> stats = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : numOfEpisodesInSeason.entrySet()) {
            SeasonStats stat = new SeasonStats(entry.getKey(), entry.getValue());
            stats.add(stat);
        }
        return stats;
    }

    private Map<Integer,Integer> getMapOfNumberOfEpisodesInSeason(List<EpisodeDto> episodes){
        Map<Integer, Integer> numOfEpisodesInSeason = new TreeMap<>();

        for (EpisodeDto episode : episodes) {
            int season = getSeasonNumber(episode); // fixme this is the consequence of holding ints in strings
            if (numOfEpisodesInSeason.containsKey(season)) {
                numOfEpisodesInSeason.merge(season, 1, Integer::sum);
            } else {
                numOfEpisodesInSeason.put(season, 1);
            }
        }
        return numOfEpisodesInSeason;
    }

    public Season getEpisodesOfSeason(int seasonId, List<EpisodeDto> episodes) {
        if(seasonId < 1) throw new RuntimeException("Id should be a positive number");
        List<EpisodeFullDto> episodeDtos = new ArrayList<>();
        for (EpisodeDto episode : episodes) {
            int season = getSeasonNumber(episode); // FIXME
            if (season == seasonId) {

                List<Character> characters = new ArrayList<>();
                StringBuilder characterNumbers = new StringBuilder();

                List<String> characterUrls = episode.getCharacterUrls();
                //FIXME divide into smaller methods
                for (String charUrl : characterUrls) {
                    int i = charUrl.length() - 1;
                    // FIXME magic numbers that are chars - two issues
                    while (charUrl.charAt(i) >= '0' && charUrl.charAt(i) <= '9') {
                        i--;
                    }
                    int characterNumber = Integer.parseInt(charUrl.substring(i + 1));
                    characterNumbers.append(characterNumber);
                    characterNumbers.append(",");
                }
                String substring = characterNumbers.substring(0, characterNumbers.length() - 1);
                String getCharsUrl = CHARACTERS_URL + substring;


                ResponseEntity<Character[]> responseEntity =
                        restTemplate.getForEntity(getCharsUrl, Character[].class);
                //FIXME potential Null Pointer Exception
                characters.addAll(List.of(responseEntity.getBody()));

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

    private int getSeasonNumber(EpisodeDto episode) {
        return Integer.parseInt(String.valueOf(episode.getSeasonAndEpisode().subSequence(1, 3)));
    }
}
