package com.example.dojorickandmorty.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class EpisodeDto {
    private int id;
    private String name;
    @Setter
    private String air_date;
    @Setter
    @JsonProperty("episode")
    private String seasonAndEpisode;
    @JsonProperty("characters")
    private List<String> characterUrls;

    public void setId(int id) {
        if (id <= 0) throw new RuntimeException("Id of episode should be greater or equal 1.");
        this.id = id;
    }

    public void setName(String name) {
        if (name.isEmpty() || name.isBlank()) throw new
                RuntimeException("Name of episode should not be blank or empty.");
        this.name = name;
    }
}
