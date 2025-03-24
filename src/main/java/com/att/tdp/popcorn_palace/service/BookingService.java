package com.att.tdp.popcorn_palace.service;


import com.att.tdp.popcorn_palace.entity.Booking;
import com.att.tdp.popcorn_palace.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public Booking bookTicket(Booking booking) {
        try {
            return bookingRepository.save(booking);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Seat is already booked for this showtime");
        }
    }
}