package com.example.tour_management.dto.tour;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class TourUpdateRequest {

    @Size(max = 60, message = "Tour name must not exceed 60 characters")
    private String tourName;

    @Positive(message = "Price must be > 0")
    private BigDecimal price;

    @Positive(message = "Quantity must be > 0")
    private Integer quantity;

    @Size(max = 1000, message = "Description too long")
    private String description;

    private String img;


    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
