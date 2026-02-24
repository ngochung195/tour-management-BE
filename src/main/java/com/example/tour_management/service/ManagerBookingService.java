package com.example.tour_management.service;

import com.example.tour_management.dto.booking.ManagerBookingResponse;
import com.example.tour_management.entity.Booking;
import com.example.tour_management.enums.BookingStatus;
import com.example.tour_management.repository.BookingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class ManagerBookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public List<ManagerBookingResponse> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(this::toManagerResponse)
                .toList();
    }

    public ManagerBookingResponse updateStatus(Integer id, String status) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        BookingStatus bookingStatus;
        try {
            bookingStatus = BookingStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid booking status: " + status);
        }

        booking.setStatus(bookingStatus);
        bookingRepository.save(booking);

        return toManagerResponse(booking);
    }


    private ManagerBookingResponse toManagerResponse(Booking b) {
        ManagerBookingResponse res = new ManagerBookingResponse();
        res.setId(b.getId());
        res.setTourName(b.getTour() != null ? b.getTour().getTourName() : null);
        res.setCustomerEmail(b.getUser() != null ? b.getUser().getEmail() : null);
        res.setQuantity(b.getQuantity());
        res.setTotal(b.getTotal());
        res.setStatus(b.getStatus() != null ? b.getStatus().name() : null);
        res.setBookingDate(b.getBookingDate());
        return res;
    }
}
