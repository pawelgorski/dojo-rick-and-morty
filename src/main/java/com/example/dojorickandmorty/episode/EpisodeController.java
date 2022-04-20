package com.example.dojorickandmorty.episode;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class EpisodeController {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @GetMapping("/episode")
    public Episode getEpisode(RestTemplate restTemplate) {
        String url = "https://rickandmortyapi.com/api/episode/1";
        return restTemplate.getForObject(url, Episode.class);
    }
}
