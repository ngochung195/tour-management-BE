package com.example.tour_management.service;

import com.example.tour_management.dto.category.CategoryRequest;
import com.example.tour_management.dto.category.CategoryResponse;
import com.example.tour_management.entity.Category;
import com.example.tour_management.exception.NotFoundException;
import com.example.tour_management.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public CategoryResponse getById(Integer id) {
        Category c = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));
        return toResponse(c);
    }

    public CategoryResponse create(CategoryRequest req) {
        Category c = new Category();
        c.setCategoryName(req.getCategoryName());
        c.setDescription(req.getDescription());

        return toResponse(categoryRepository.save(c));
    }

    public CategoryResponse update(Integer id, CategoryRequest req) {
        Category old = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));

        old.setCategoryName(req.getCategoryName());
        old.setDescription(req.getDescription());

        return toResponse(categoryRepository.save(old));
    }

    public void delete(Integer id) {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundException("Category not found");
        }
        categoryRepository.deleteById(id);
    }

    private CategoryResponse toResponse(Category c) {
        CategoryResponse res = new CategoryResponse();
        res.setId(c.getId());
        res.setCategoryName(c.getCategoryName());
        res.setDescription(c.getDescription());
        return res;
    }
}
