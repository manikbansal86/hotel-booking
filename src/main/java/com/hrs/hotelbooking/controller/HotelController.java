package com.hrs.hotelbooking.controller;

import com.hrs.hotelbooking.analytics.AnalyticsLogger;
import com.hrs.hotelbooking.constants.Constants;
import com.hrs.hotelbooking.model.Hotel;
import com.hrs.hotelbooking.repository.HotelRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
@RequiredArgsConstructor
@Tag(name = "Hotel API", description = "Operations related to hotel searches")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "404", description = "Resource not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
})
public class HotelController {

    private final HotelRepository hotelRepository;

    @Operation(summary = "Search hotels by location (optional filter)")
    @ApiResponse(responseCode = "200", description = "Hotels retrieved successfully")
    @GetMapping
    public List<Hotel> searchHotels(@RequestParam(required = false) String location) {
        List<Hotel> results;
        AnalyticsLogger.log("Request received for fetching hotels, location: "+location);
        if (location == null || location.isBlank()) {
            results = hotelRepository.findAll();
            AnalyticsLogger.log(Constants.HOTEL_FETCHED, "Total number of Hotels fetched=" + results.size());
        } else {
            results = hotelRepository.findByLocationContainingIgnoreCase(location);
            AnalyticsLogger.log(Constants.HOTEL_FETCHED_BY_LOCATION, String.format("Hotel fetched by location:%s , results=%d", location, results.size()));
        }
        return results;
    }
}
