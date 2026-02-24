package com.example.tour_management.repository;

import com.example.tour_management.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TourRepository extends JpaRepository<Tour, Integer> {
}
