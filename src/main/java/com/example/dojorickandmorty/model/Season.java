package com.example.dojorickandmorty.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Season {
    private int season; // TODO what does this variable hold? is it the season number? id?
    private List<EpisodeFullDto> episodes;
}
