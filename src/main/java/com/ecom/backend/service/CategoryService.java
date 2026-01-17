package com.ecom.backend.service;

import com.ecom.backend.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    public List<CategoryDto> getAllCategories();

    public CategoryDto createCategory(CategoryDto categoryDto);

    public CategoryDto getCategoryById(long id);
}
