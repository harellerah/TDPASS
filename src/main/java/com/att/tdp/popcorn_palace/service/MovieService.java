package com.att.tdp.popcorn_palace.service;


import com.att.tdp.popcorn_palace.entity.Movie;
import com.att.tdp.popcorn_palace.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public void updateMovie(String movieTitle, Movie movie) {
        Movie existing = movieRepository.findByTitle(movieTitle)
                .orElseThrow(() -> new RuntimeException("Movie not found: " + movieTitle));
        existing.setTitle(movie.getTitle());
        existing.setGenre(movie.getGenre());
        existing.setDuration(movie.getDuration());
        existing.setRating(movie.getRating());
        existing.setReleaseYear(movie.getReleaseYear());
        movieRepository.save(existing);
    }

    public void deleteMovie(String movieTitle) {
        Movie movie = movieRepository.findByTitle(movieTitle)
                .orElseThrow(() -> new RuntimeException("Movie not found: " + movieTitle));
        movieRepository.delete(movie);
    }
}