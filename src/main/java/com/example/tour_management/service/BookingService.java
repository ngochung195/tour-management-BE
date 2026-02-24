package com.example.tour_management.service;

import com.example.tour_management.dto.booking.BookingRequest;
import com.example.tour_management.dto.booking.BookingResponse;
import com.example.tour_management.entity.Booking;
import com.example.tour_management.entity.Tour;
import com.example.tour_management.entity.User;
import com.example.tour_management.enums.BookingStatus;
import com.example.tour_management.exception.NotFoundException;
import com.example.tour_management.repository.BookingRepository;
import com.example.tour_management.repository.TourRepository;
import com.example.tour_management.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TourRepository tourRepository;

    public List<BookingResponse> getAll() {
        return bookingRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public BookingResponse getById(Integer id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Booking not found"));
        return toResponse(booking);
    }

    @Transactional
    public BookingResponse create(BookingRequest req, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Tour tour = tourRepository.findById(req.getTourId())
                .orElseThrow(() -> new NotFoundException("Tour not found"));

        int quantity = req.getQuantity();

        if (tour.getQuantity() < quantity) {
            throw new RuntimeException("Not enough tour quantity");
        }

        tour.setQuantity(tour.getQuantity() - quantity);
        tourRepository.save(tour);

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setTour(tour);
        booking.setQuantity(quantity);
        booking.setTotal(
                tour.getPrice().multiply(
                        java.math.BigDecimal.valueOf(quantity)
                )
        );
        booking.setStatus(BookingStatus.PENDING);
        booking.setBookingDate(LocalDateTime.now());

        Booking saved = bookingRepository.save(booking);
        saved.setBookingCode(String.format("BK-%03d", saved.getId()));
        bookingRepository.save(saved);

        return toResponse(saved);
    }

    public BookingResponse updateStatus(Integer id, String status) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Booking not found"));

        try {
            booking.setStatus(BookingStatus.valueOf(status));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid booking status: " + status);
        }

        return toResponse(bookingRepository.save(booking));
    }

    public void delete(Integer id) {
        if (!bookingRepository.existsById(id)) {
            throw new NotFoundException("Booking not found");
        }
        bookingRepository.deleteById(id);
    }

    public void cancel(Integer id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Booking not found"));

        booking.setStatus(BookingStatus.CANCELLED);

        Tour tour = booking.getTour();

        tour.setQuantity(
                tour.getQuantity() + booking.getQuantity()
        );

        bookingRepository.save(booking);
    }

    public List<BookingResponse> getByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

        return bookingRepository.findByUserId(user.getId())
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }


    private BookingResponse toResponse(Booking b) {
        BookingResponse res = new BookingResponse();
        res.setId(b.getId());

        if (b.getUser() != null) {
            res.setUserId(b.getUser().getId());
        }

        if (b.getTour() != null) {
            res.setTourId(b.getTour().getId());
            res.setTourName(b.getTour().getTourName());
        }

        res.setTotal(b.getTotal());
        res.setStatus(b.getStatus().name());
        res.setBookingDate(b.getBookingDate());
        res.setBookingCode(b.getBookingCode());

        return res;
    }
}
