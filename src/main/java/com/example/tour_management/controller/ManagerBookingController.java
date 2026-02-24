package com.example.tour_management.controller;

import com.example.tour_management.dto.booking.ManagerBookingResponse;
import com.example.tour_management.service.ManagerBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/manager/bookings")
public class ManagerBookingController {
    @Autowired
    private ManagerBookingService managerBookingService;

    @GetMapping
    public List<ManagerBookingResponse> getAll(){
        return managerBookingService.getAllBookings();
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Integer id,
            @RequestParam String status
    ) {
        try {
            return ResponseEntity.ok(
                    managerBookingService.updateStatus(id, status)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}