package com.example.tour_management.controller;

import com.example.tour_management.dto.tour.TourRequest;
import com.example.tour_management.dto.tour.TourResponse;
import com.example.tour_management.service.TourService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manager/tours")
@PreAuthorize("hastRole('MANAGER')")
public class ManagerTourController {
    private final TourService tourService;

    public ManagerTourController(TourService tourService){
        this.tourService = tourService;
    }

    @GetMapping
    public List<TourResponse> getAll(){
        return tourService.getAll();
    }

    @PostMapping
    public TourResponse crete(@RequestBody TourRequest req){
        return tourService.create(req);
    }

    @PutMapping("/{id}")
    public TourResponse update(@PathVariable Integer id, @RequestBody TourRequest req){
        return tourService.update(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        tourService.delete(id);
    }
}
