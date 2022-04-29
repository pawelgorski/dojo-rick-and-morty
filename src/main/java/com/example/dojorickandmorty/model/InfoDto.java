package com.example.dojorickandmorty.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class InfoDto {
    private int count;
    private int pages;
    private String next; // TODO name should be more descriptive - nextUrl maybe?
    private String prev;
}
