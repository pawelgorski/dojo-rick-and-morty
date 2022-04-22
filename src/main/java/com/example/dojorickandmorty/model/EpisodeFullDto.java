package com.example.dojorickandmorty.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EpisodeFullDto {
    private int id;
    private String name;
    private String air_date;
    private String episode;
    private List<Character> characters;
//    private String url;
//    private String created;
}
