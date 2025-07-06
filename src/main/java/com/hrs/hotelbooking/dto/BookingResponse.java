package com.hrs.hotelbooking.dto;

import com.hrs.hotelbooking.model.BookingStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingResponse {
    private Long bookingId;
    private Long hotelId;
    private Long userId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private BookingStatus status;
}
