package com.example.dojorickandmorty.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EpisodeLightDto {
    private int id;
    private String name;
    private String episode; // TODO why String? what does this variable contain?
    private String air_date; // TODO why String and not Date?
}
