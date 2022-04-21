package com.example.dojorickandmorty.service;

import com.example.dojorickandmorty.model.EpisodeDto;
import com.example.dojorickandmorty.model.EpisodeLightweightDto;
import com.example.dojorickandmorty.model.RickAndMorty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
//@Slf4j
public class EpisodeService {
    public static final String RICK_AND_MORTY_URL = "https://rickandmortyapi.com/api/episode?page={id}";
    private final RestTemplate restTemplate = new RestTemplate();

    //    public List<EpisodeDto> getEpisodes() throws JsonProcessingException {
    public String getEpisodesCounter() throws JsonProcessingException {

        List<EpisodeDto> episodes = getAllEpisodes();
        Map<String, Integer> episodesCount = getNumberOfSeasonsEpisodes(episodes);
        String json = convertMapToJson(episodesCount);
//        log.info(String.valueOf(pagesCounter));
        return json;
    }



    private List<EpisodeDto> getAllEpisodes() {
        String id = String.valueOf(1);
        ResponseEntity<RickAndMorty> responseEntity =
                restTemplate.getForEntity(RICK_AND_MORTY_URL, RickAndMorty.class, id);

        RickAndMorty rickAndMorty = responseEntity.getBody();

        List<EpisodeDto> episodes = rickAndMorty.getResults();

        int pagesCounter = rickAndMorty.getInfo().getPages();


        for (int i = 2; i <= pagesCounter; i++) {
            id = String.valueOf(i);
            responseEntity = restTemplate.getForEntity(RICK_AND_MORTY_URL, RickAndMorty.class, id);
            episodes.addAll(responseEntity.getBody().getResults());
        }
        return episodes;
    }

    private Map<String, Integer> getNumberOfSeasonsEpisodes(List<EpisodeDto> episodes) {
        if (episodes.isEmpty()) return null;
        int counter = 0;
        Map<String, Integer> map = new TreeMap<>();

        for (EpisodeDto episode : episodes) {
            String season = String.valueOf(episode.getEpisode().subSequence(0, 3));
            if (map.containsKey(season)) {
                map.merge(season, 1, Integer::sum);
            } else {
                map.put(season, 1);
            }
        }
        return map;
    }

    private List<EpisodeLightweightDto> getSeasonsEpisodes(List<EpisodeLightweightDto> episodes, String seasonChosen) {
        if (episodes.isEmpty()) return null;

        List<EpisodeLightweightDto> episodesFromSeason = new ArrayList<>();
        for (EpisodeLightweightDto episode : episodes) {
            String season = String.valueOf(episode.getEpisode().subSequence(0, 3));
            if (season.equals(seasonChosen)) {
                episodesFromSeason.add(episode);
            }
        }
        return episodesFromSeason;
    }

    private String convertMapToJson(Map<String, Integer> episodesCount) throws JsonProcessingException {
        ObjectMapper objMapper = new ObjectMapper();

        String json = objMapper.writeValueAsString(episodesCount);
        return json;
    }
}
