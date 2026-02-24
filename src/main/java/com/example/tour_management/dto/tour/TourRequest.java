package com.example.tour_management.dto.tour;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TourRequest {

        @NotBlank(message = "Tour name is required")
        @Size(max = 60, message = "Tour name must not exceed 60 characters")
        private String tourName;

        @NotNull(message = "Category is required")
        private Integer categoryId;

        @NotNull(message = "Price is required")
        @Positive(message = "Price must be > 0")
        private BigDecimal price;

        @NotNull(message = "Quantity is required")
        @Positive(message = "Quantity must be > 0")
        private Integer quantity;

        @Size(max = 1000, message = "Description too long")
        private String description;

        @NotNull(message = "Start date is required")
        private LocalDate startDate;

        @NotNull(message = "End date is required")
        private LocalDate endDate;

        private String img;


        public String getTourName() {
                return tourName;
        }

        public void setTourName(String tourName) {
                this.tourName = tourName;
        }

        public Integer getCategoryId() {
                return categoryId;
        }

        public void setCategoryId(Integer categoryId) {
                this.categoryId = categoryId;
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

        public LocalDate getStartDate() {
                return startDate;
        }

        public void setStartDate(LocalDate startDate) {
                this.startDate = startDate;
        }

        public LocalDate getEndDate() {
                return endDate;
        }

        public void setEndDate(LocalDate endDate) {
                this.endDate = endDate;
        }

        public String getImg() {
                return img;
        }

        public void setImg(String img) {
                this.img = img;
        }
}
