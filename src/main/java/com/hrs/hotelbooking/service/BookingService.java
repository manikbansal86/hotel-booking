package com.hrs.hotelbooking.service;

import com.hrs.hotelbooking.dto.BookingRequest;
import com.hrs.hotelbooking.model.Booking;


public interface BookingService {
    Booking createBooking(BookingRequest request);
    Booking updateBooking(Long id, BookingRequest request);
    Booking getBooking(Long id);
    void cancelBooking(Long id);
}
