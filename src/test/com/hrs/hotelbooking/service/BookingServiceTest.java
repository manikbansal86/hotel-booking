package hrs.hotelbooking.service;

import com.hrs.hotelbooking.dto.BookingRequest;
import com.hrs.hotelbooking.model.Booking;
import com.hrs.hotelbooking.model.BookingStatus;
import com.hrs.hotelbooking.model.Hotel;
import com.hrs.hotelbooking.repository.BookingRepository;
import com.hrs.hotelbooking.repository.HotelRepository;
import com.hrs.hotelbooking.service.impl.BookingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBooking_HappyPath() {
        Hotel hotel = new Hotel();
        hotel.setId(1L);
        hotel.setAvailableRooms(5);

        BookingRequest request = new BookingRequest();
        request.setHotelId(1L);
        request.setUserId(1001L);
        request.setCheckInDate(LocalDate.now().plusDays(1));
        request.setCheckOutDate(LocalDate.now().plusDays(3));

        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        when(hotelRepository.save(any())).thenReturn(hotel);
        when(bookingRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Booking booking = bookingService.createBooking(request);

        assertNotNull(booking);
        assertEquals(BookingStatus.BOOKED, booking.getStatus());
        assertEquals(1001L, booking.getUserId());
        assertEquals(1L, booking.getHotelId());

        verify(hotelRepository).save(hotel);
        verify(bookingRepository).save(any(Booking.class));
    }

    @Test
    void testUpdateBooking_HappyPath() {
        Booking existing = new Booking();
        existing.setId(1L);
        existing.setCheckInDate(LocalDate.of(2025, 7, 10));
        existing.setCheckOutDate(LocalDate.of(2025, 7, 12));

        BookingRequest request = new BookingRequest();
        request.setCheckInDate(LocalDate.of(2025, 7, 15));
        request.setCheckOutDate(LocalDate.of(2025, 7, 18));

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(bookingRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Booking updated = bookingService.updateBooking(1L, request);

        assertEquals(LocalDate.of(2025, 7, 15), updated.getCheckInDate());
        assertEquals(LocalDate.of(2025, 7, 18), updated.getCheckOutDate());
    }

    @Test
    void testGetBooking_HappyPath() {
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setUserId(101L);

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        Booking result = bookingService.getBooking(1L);

        assertEquals(1L, result.getId());
        assertEquals(101L, result.getUserId());
    }

    @Test
    void testCancelBooking_HappyPath() {
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setHotelId(2L);
        booking.setStatus(BookingStatus.BOOKED);

        Hotel hotel = new Hotel();
        hotel.setId(2L);
        hotel.setAvailableRooms(5);

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        when(hotelRepository.findById(2L)).thenReturn(Optional.of(hotel));

        bookingService.cancelBooking(1L);

        assertEquals(BookingStatus.CANCELLED, booking.getStatus());
        assertEquals(6, hotel.getAvailableRooms()); // incremented
        verify(hotelRepository).save(hotel);
        verify(bookingRepository).save(booking);
    }

}
