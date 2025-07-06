package com.hrs.hotelbooking.controller;

import com.hrs.hotelbooking.analytics.AnalyticsLogger;
import com.hrs.hotelbooking.constants.Constants;
import com.hrs.hotelbooking.dto.BookingRequest;
import com.hrs.hotelbooking.dto.BookingResponse;
import com.hrs.hotelbooking.model.Booking;
import com.hrs.hotelbooking.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@Tag(name = "Booking API", description = "Operations related to hotel bookings")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Bad request (e.g., invalid input)"),
        @ApiResponse(responseCode = "404", description = "Resource not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
})
public class BookingController {

    private final BookingService bookingService;

    @Operation(summary = "Create a new hotel booking")
    @ApiResponse(responseCode = "201", description = "Booking created successfully")
    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@RequestBody @Valid BookingRequest request) {
        AnalyticsLogger.log(Constants.BOOKING_CREATED, "Request received for booking creation : "+request.toString());
        Booking booking = bookingService.createBooking(request);
        return new ResponseEntity<>(toResponse(booking), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing booking")
    @ApiResponse(responseCode = "200", description = "Booking updated successfully")
    @PutMapping("/{id}")
    public ResponseEntity<BookingResponse> updateBooking(@PathVariable Long id,
                                                         @RequestBody @Valid BookingRequest request) {
        AnalyticsLogger.log(Constants.BOOKING_UPDATED, "Request received for booking updation : "+request.toString());
        Booking updated = bookingService.updateBooking(id, request);
        return ResponseEntity.ok(toResponse(updated));
    }

    @Operation(summary = "Retrieve a booking by ID")
    @ApiResponse(responseCode = "200", description = "Booking found")
    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBooking(@PathVariable Long id) {
        AnalyticsLogger.log(Constants.BOOKING_FETCHED, "Fetch booking for id : "+id);
        Booking booking = bookingService.getBooking(id);
        return ResponseEntity.ok(toResponse(booking));
    }

    @Operation(summary = "Cancel a booking by ID")
    @ApiResponse(responseCode = "204", description = "Booking cancelled successfully")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id) {
        AnalyticsLogger.log(Constants.BOOKING_CANCELLED, "Cancelling booking for bookingId=" + id);
        bookingService.cancelBooking(id);
        return ResponseEntity.noContent().build();
    }

    // DTO mapping method
    private BookingResponse toResponse(Booking booking) {
        BookingResponse response = new BookingResponse();
        response.setBookingId(booking.getId());
        response.setHotelId(booking.getHotelId());
        response.setUserId(booking.getUserId());
        response.setCheckInDate(booking.getCheckInDate());
        response.setCheckOutDate(booking.getCheckOutDate());
        response.setStatus(booking.getStatus());
        return response;
    }
}