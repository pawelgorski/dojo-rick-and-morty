package com.example.dojorickandmorty.service;

import com.example.dojorickandmorty.model.EpisodeDto;
import com.example.dojorickandmorty.model.EpisodeLightDto;
import com.example.dojorickandmorty.model.RickAndMorty;
import com.example.dojorickandmorty.model.RickAndMortyLight;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class EpisodeService {
    public static final String RICK_AND_MORTY_URL = "https://rickandmortyapi.com/api/episode?page={id}";
    private final RestTemplate restTemplate = new RestTemplate();

    public List<EpisodeLightDto> getAllEpisodes() {
        String id = String.valueOf(1);
        ResponseEntity<RickAndMortyLight> responseEntity =
                restTemplate.getForEntity(RICK_AND_MORTY_URL, RickAndMortyLight.class, id);

        RickAndMortyLight rickAndMorty = responseEntity.getBody();

        List<EpisodeLightDto> episodes = rickAndMorty.getResults();

        int pagesCounter = rickAndMorty.getInfo().getPages();


        for (int i = 2; i <= pagesCounter; i++) {
            id = String.valueOf(i);
            responseEntity = restTemplate.getForEntity(RICK_AND_MORTY_URL, RickAndMortyLight.class, id);
            episodes.addAll(responseEntity.getBody().getResults());
        }
        return episodes;
    }

    public List<EpisodeDto> getAllEpisodesFull() {
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
}
