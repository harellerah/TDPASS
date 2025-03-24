package com.att.tdp.popcorn_palace.service;


import com.att.tdp.popcorn_palace.entity.Showtime;
import com.att.tdp.popcorn_palace.repository.ShowtimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowtimeService {
    @Autowired
    private ShowtimeRepository showtimeRepository;

    public Showtime getShowtimeById(Long id) {
        return showtimeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Showtime not found: " + id));
    }

    public Showtime addShowtime(Showtime showtime) {
//        showtime.setTheaterId(1L);
        if (showtime.getStartTime().isAfter(showtime.getEndTime())) {
            throw new RuntimeException("Start time must be before end time");
        }
        List<Showtime> overlaps = showtimeRepository.findOverlappingShowtimes(
                showtime.getTheater(), showtime.getStartTime(), showtime.getEndTime());
        if (!overlaps.isEmpty()) {
            throw new RuntimeException("Showtime overlaps with an existing one in the same theater");
        }
//        showtime.setTheaterId(showtime.getId());
        return showtimeRepository.save(showtime);
    }

    public void updateShowtime(Long id, Showtime showtime) {
        Showtime existing = showtimeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Showtime not found: " + id));
        existing.setMovieId(showtime.getMovieId());
        existing.setTheater(showtime.getTheater());
        existing.setStartTime(showtime.getStartTime());
        existing.setEndTime(showtime.getEndTime());
        existing.setPrice(showtime.getPrice());
        addShowtime(existing); // Reuses overlap check
    }

    public void deleteShowtime(Long id) {
        showtimeRepository.deleteById(id);
    }
}