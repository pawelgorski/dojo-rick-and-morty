package com.example.dojorickandmorty.service;

import com.example.dojorickandmorty.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.awt.image.Kernel;
import java.util.List;
import java.util.Objects;

@Service
public class EpisodeService {
    public static final String RICK_AND_MORTY_URL = "https://rickandmortyapi.com/api/episode?page={id}";
    private final RestTemplate restTemplate = new RestTemplate();

    public List<EpisodeDto> getAllEpisodes() {
        int id = 1;
        ResponseEntity<RickAndMorty> responseEntity =
                restTemplate.getForEntity(RICK_AND_MORTY_URL, RickAndMorty.class, id);

        RickAndMorty rickAndMorty = responseEntity.getBody();

        List<EpisodeDto> episodes;
        int pagesCounter;
        if (rickAndMorty != null) {
            episodes = rickAndMorty.getResults();
            pagesCounter = rickAndMorty.getInfo().getPages();
        } else {
            throw new RuntimeException("Response body is null!");
        }

        for (int i = 2; i <= pagesCounter; i++) {
            responseEntity = restTemplate.getForEntity(RICK_AND_MORTY_URL, RickAndMorty.class, i);
            episodes.addAll(Objects.requireNonNull(responseEntity.getBody()).getResults());
        }
        return episodes;
    }
}
