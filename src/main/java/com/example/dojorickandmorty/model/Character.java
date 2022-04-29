package com.example.dojorickandmorty.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Character {
    private int id; // TODO verification on setter -> can id be <0?
    private String name; // TODO can name be empty or made of 0-9!@# chars?
    private String image;
}
