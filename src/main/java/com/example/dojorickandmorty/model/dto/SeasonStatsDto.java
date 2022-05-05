package com.example.dojorickandmorty.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SeasonStatsDto {
    private int numberOfSeason;
    private int numberOfEpisodes;
}
