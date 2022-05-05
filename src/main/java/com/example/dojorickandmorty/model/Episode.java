package com.example.dojorickandmorty.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Episode {
    @NotNull(message = "Id of episode cannot be null.")
    @Min(value = 1, message = "Id of episode should be greater or equal 1.")
    private int id;
    @NotBlank(message = "Name of episode cannot be blank.")
    private String name;
    private String air_date;
    @JsonProperty("episode")
    private String seasonAndEpisode;
    @JsonProperty("characters")
    private List<String> characterUrls;
}
