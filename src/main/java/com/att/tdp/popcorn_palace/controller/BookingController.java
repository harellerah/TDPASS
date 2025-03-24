package com.att.tdp.popcorn_palace.controller;


import com.att.tdp.popcorn_palace.entity.Booking;
import com.att.tdp.popcorn_palace.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<Map<String, String>> bookTicket(@Valid @RequestBody Booking booking) {
        Booking savedBooking = bookingService.bookTicket(booking);
        Map<String, String> response = new HashMap<>();
        response.put("bookingId", savedBooking.getId().toString());
        return ResponseEntity.ok(response);
    }
}