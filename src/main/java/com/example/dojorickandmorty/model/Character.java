package com.example.dojorickandmorty.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Character {
    private int id;
    private String name;
    @Setter
    private String image;

    public void setId(int id) {
        if(id <= 0) throw new RuntimeException("Id of character should be greater or equal 1.");
        this.id = id;
    }

    public void setName(String name) {
        if(name.isEmpty() || name.isBlank()) throw new
                RuntimeException("Name of character should not be blank or empty.");
        this.name = name;
    }
}
