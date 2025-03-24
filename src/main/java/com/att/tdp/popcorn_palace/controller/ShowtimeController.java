package com.att.tdp.popcorn_palace.controller;


import com.att.tdp.popcorn_palace.entity.Showtime;
import com.att.tdp.popcorn_palace.service.ShowtimeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/showtimes")
public class ShowtimeController {
    @Autowired
    private ShowtimeService showtimeService;

    @GetMapping("/{showtimeId}")
    public ResponseEntity<Showtime> getShowtimeById(@PathVariable Long showtimeId) {
        return ResponseEntity.ok(showtimeService.getShowtimeById(showtimeId));
    }

    @PostMapping
    public ResponseEntity<Showtime> addShowtime(@Valid @RequestBody Showtime showtime) {
        return ResponseEntity.ok(showtimeService.addShowtime(showtime));
    }

    @PostMapping("/update/{showtimeId}")
    public ResponseEntity<Void> updateShowtime(@PathVariable Long showtimeId, @Valid @RequestBody Showtime showtime) {
        showtimeService.updateShowtime(showtimeId, showtime);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{showtimeId}")
    public ResponseEntity<Void> deleteShowtime(@PathVariable Long showtimeId) {
        showtimeService.deleteShowtime(showtimeId);
        return ResponseEntity.ok().build();
    }
}