package com.example.tour_management.controller;

import com.example.tour_management.dto.tour.TourRequest;
import com.example.tour_management.dto.tour.TourResponse;
import com.example.tour_management.service.TourService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tours")
public class TourController {

    @Autowired
    private TourService tourService;

    @GetMapping
    public ResponseEntity<List<TourResponse>> getAll() {
        return ResponseEntity.ok(tourService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TourResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(tourService.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody TourRequest req) {
        return ResponseEntity.ok(tourService.create(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Integer id,
            @Valid @RequestBody TourRequest req) {
        return ResponseEntity.ok(tourService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        tourService.delete(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}
