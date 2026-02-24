package com.example.tour_management.service;

import com.example.tour_management.dto.tour.TourRequest;
import com.example.tour_management.dto.tour.TourResponse;
import com.example.tour_management.entity.Category;
import com.example.tour_management.entity.Tour;
import com.example.tour_management.exception.NotFoundException;
import com.example.tour_management.repository.CategoryRepository;
import com.example.tour_management.repository.TourRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TourService {

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<TourResponse> getAll() {
        return tourRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public TourResponse getById(Integer id) {
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tour not found"));
        return mapToResponse(tour);
    }

    public TourResponse create(TourRequest request) {

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found"));

        Tour tour = new Tour();
        tour.setTourName(request.getTourName());
        tour.setPrice(request.getPrice());
        tour.setQuantity(request.getQuantity());
        tour.setDescription(request.getDescription());
        tour.setImg(request.getImg());
        tour.setStartDate(request.getStartDate());
        tour.setEndDate(request.getEndDate());
        tour.setCategory(category);

        return mapToResponse(tourRepository.save(tour));
    }

    public TourResponse update(Integer id, TourRequest request) {

        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tour not found"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found"));

        tour.setTourName(request.getTourName());
        tour.setPrice(request.getPrice());
        tour.setQuantity(request.getQuantity());
        tour.setDescription(request.getDescription());
        tour.setImg(request.getImg());
        tour.setStartDate(request.getStartDate());
        tour.setEndDate(request.getEndDate());
        tour.setCategory(category);

        return mapToResponse(tourRepository.save(tour));
    }

    public void delete(Integer id) {
        if (!tourRepository.existsById(id)) {
            throw new NotFoundException("Tour not found");
        }
        tourRepository.deleteById(id);
    }

    private static final String IMAGE_BASE_URL = "http://localhost:8080/tours/";

    private TourResponse mapToResponse(Tour tour) {
        TourResponse res = new TourResponse();
        res.setId(tour.getId());
        res.setTourName(tour.getTourName());
        res.setQuantity(tour.getQuantity());
        res.setStartDate(tour.getStartDate());
        res.setEndDate(tour.getEndDate());
        res.setPrice(tour.getPrice());

        String img = tour.getImg();
        if (img != null && !img.isBlank()) {
            if (img.startsWith("http")) {
                res.setImg(img);
            } else if (img.startsWith("/tours/")) {
                res.setImg("http://localhost:8080" + img);
            } else {
                res.setImg(IMAGE_BASE_URL + img);
            }
        }

        if (tour.getCategory() != null) {
            res.setCategoryId(tour.getCategory().getId());
            res.setCategoryName(tour.getCategory().getCategoryName());
        }

        return res;
    }
}
