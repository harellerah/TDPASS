package com.att.tdp.popcorn_palace.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "bookings", uniqueConstraints = @UniqueConstraint(columnNames = {"showtime_id", "seat_number"}))
@Getter
@Setter
public class Booking {
    @Id
    @GeneratedValue
    private UUID id;

    @NotNull(message = "Showtime ID is mandatory")
    @Column(name = "showtime_id")
    private Long showtimeId;

    @NotNull(message = "Seat number is mandatory")
    private Integer seatNumber;

    @NotNull(message = "User ID is mandatory")
    private UUID userId;
}