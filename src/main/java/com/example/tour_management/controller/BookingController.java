package com.example.tour_management.controller;

import com.example.tour_management.dto.booking.BookingRequest;
import com.example.tour_management.dto.booking.BookingResponse;
import com.example.tour_management.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponse> create(
            @Valid @RequestBody BookingRequest req,
            Authentication authentication
    ) {
        String email = authentication.getName();
        return ResponseEntity.ok(bookingService.create(req, email));
    }

    @GetMapping("/my")
    public ResponseEntity<List<BookingResponse>> getMyBookings(
            Authentication authentication
    ) {
        String email = authentication.getName();
        return ResponseEntity.ok(bookingService.getByEmail(email));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelBooking(@PathVariable Integer id)
    {
        bookingService.cancel(id);
        return ResponseEntity.ok().build();
    }
}
