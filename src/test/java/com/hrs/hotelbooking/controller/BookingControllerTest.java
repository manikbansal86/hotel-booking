package com.hrs.hotelbooking.controller;


import com.hrs.hotelbooking.HotelBookingApplication;
import com.hrs.hotelbooking.dto.BookingRequest;
import com.hrs.hotelbooking.model.Booking;
import com.hrs.hotelbooking.model.BookingStatus;
import com.hrs.hotelbooking.service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrs.hotelbooking.controller.BookingController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
@ContextConfiguration(classes = HotelBookingApplication.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateBooking_HappyPath() throws Exception {
        BookingRequest request = new BookingRequest();
        request.setHotelId(1L);
        request.setUserId(1001L);
        request.setCheckInDate(LocalDate.now().plusDays(1));
        request.setCheckOutDate(LocalDate.now().plusDays(3));

        Booking mockBooking = new Booking();
        mockBooking.setId(1L);
        mockBooking.setHotelId(1L);
        mockBooking.setUserId(1001L);
        mockBooking.setCheckInDate(request.getCheckInDate());
        mockBooking.setCheckOutDate(request.getCheckOutDate());
        mockBooking.setStatus(BookingStatus.BOOKED);

        Mockito.when(bookingService.createBooking(Mockito.any())).thenReturn(mockBooking);

        mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookingId").value(1L))
                .andExpect(jsonPath("$.hotelId").value(1L))
                .andExpect(jsonPath("$.userId").value(1001L));
    }
}
