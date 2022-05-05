package com.example.dojorickandmorty.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Character {
    @NotNull(message = "Id of character cannot be null.")
    @Min(value = 1, message = "Id of character should be greater or equal 1.")
    private int id;
    @NotBlank(message = "Name of character cannot be blank.")
    private String name;
    private String image;
}
