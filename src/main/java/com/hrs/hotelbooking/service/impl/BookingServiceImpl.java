package com.hrs.hotelbooking.service.impl;

import com.hrs.hotelbooking.analytics.AnalyticsLogger;
import com.hrs.hotelbooking.constants.Constants;
import com.hrs.hotelbooking.dto.BookingRequest;
import com.hrs.hotelbooking.exception.BadRequestException;
import com.hrs.hotelbooking.exception.ResourceNotFoundException;
import com.hrs.hotelbooking.model.Booking;
import com.hrs.hotelbooking.model.BookingStatus;
import com.hrs.hotelbooking.model.Hotel;
import com.hrs.hotelbooking.repository.BookingRepository;
import com.hrs.hotelbooking.repository.HotelRepository;
import com.hrs.hotelbooking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;

    @Override
    public Booking createBooking(BookingRequest request) {
        if (request.getCheckOutDate().isBefore(request.getCheckInDate())) {
            throw new BadRequestException("Check-out date must be after check-in date");
        }

        Hotel hotel = hotelRepository.findById(request.getHotelId())
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found"));

        if (hotel.getAvailableRooms() <= 0) {
            throw new RuntimeException("No available rooms");
        }

        Booking booking = new Booking();
        booking.setUserId(request.getUserId());
        booking.setHotelId(request.getHotelId());
        booking.setCheckInDate(request.getCheckInDate());
        booking.setCheckOutDate(request.getCheckOutDate());
        booking.setStatus(BookingStatus.BOOKED);

        hotel.setAvailableRooms(hotel.getAvailableRooms() - 1);
        hotelRepository.save(hotel);
        Booking saved = bookingRepository.save(booking);

        AnalyticsLogger.log(Constants.BOOKING_CREATED, String.format(
                "userId=%d hotelId=%d checkIn=%s checkOut=%s",
                saved.getUserId(), saved.getHotelId(),
                saved.getCheckInDate(), saved.getCheckOutDate()
        ));

        return saved;
    }

    @Override
    public Booking updateBooking(Long id, BookingRequest request) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        booking.setCheckInDate(request.getCheckInDate());
        booking.setCheckOutDate(request.getCheckOutDate());
        Booking updated = bookingRepository.save(booking);

        AnalyticsLogger.log(Constants.BOOKING_UPDATED, String.format(
                "bookingId=%d newCheckIn=%s newCheckOut=%s",
                updated.getId(), updated.getCheckInDate(), updated.getCheckOutDate()
        ));

        return updated;
    }

    @Override
    public Booking getBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        AnalyticsLogger.log(Constants.BOOKING_FETCHED, "bookingId=" + booking.getId());

        return booking;
    }

    @Override
    public void cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            return;
        }

        Hotel hotel = hotelRepository.findById(booking.getHotelId())
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found"));

        hotel.setAvailableRooms(hotel.getAvailableRooms() + 1);
        hotelRepository.save(hotel);

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
        AnalyticsLogger.log(Constants.BOOKING_CANCELLED, "bookingId=" + id);
    }
}
