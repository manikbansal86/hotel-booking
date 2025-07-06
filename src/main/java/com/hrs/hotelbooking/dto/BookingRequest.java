package com.hrs.hotelbooking.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingRequest {
    private Long hotelId;
    private Long userId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
