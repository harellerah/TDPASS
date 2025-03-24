package com.att.tdp.popcorn_palace.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "movies")
@Getter
@Setter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Title is mandatory")
    @Column(unique = true)
    private String title;

    @NotBlank(message = "Genre is mandatory")
    private String genre;

    @Positive(message = "Duration must be positive")
    private int duration;

    @Min(value = 0, message = "Rating must be between 0 and 10")
    @Max(value = 10, message = "Rating must be between 0 and 10")
    private float rating;

    @Min(value = 1888, message = "Release year must be valid")
    private int releaseYear;
}