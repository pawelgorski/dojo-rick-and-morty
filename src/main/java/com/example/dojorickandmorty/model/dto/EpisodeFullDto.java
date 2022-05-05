package com.example.dojorickandmorty.model.dto;

import com.example.dojorickandmorty.model.Character;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class EpisodeFullDto {
    @NotNull(message = "Id of episode cannot be null.")
    @Min(value = 1, message = "Id of episode should be greater or equal 1.")
    private int id;
    @NotBlank(message = "Name of episode cannot be blank.")
    private String name;
    private String air_date;
    private String seasonAndEpisode;
    private List<Character> characters;
}
