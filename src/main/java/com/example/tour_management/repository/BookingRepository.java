package com.example.tour_management.repository;

import com.example.tour_management.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer>{

    List<Booking> findByUserId(Integer userId);
}
