package com.example.tour_management.dto.category;

import jakarta.validation.constraints.Size;

public class CategoryRequest {

    @Size(max = 50, message = "Category name must not exceed 50 characters")
    private String categoryName;

    @Size(max = 500, message = "Description too long")
    private String description;



    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
