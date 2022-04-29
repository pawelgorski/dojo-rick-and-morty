package com.example.dojorickandmorty.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EpisodeDto {
    private int id; //TODO validation on setters
    private String name;
    private String air_date;
    private String episode;
    private List<String> characters; // TODO why list of Strings and not Characters?
//    private String url;
//    private String created;
}
