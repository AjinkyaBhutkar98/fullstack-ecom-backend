package com.ecom.backend.service;

import com.ecom.backend.dto.CategoryDto;
import com.ecom.backend.dto.PagedResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {

    public PagedResponse<CategoryDto> getAllCategories(int page, int size, String sortBy, String sortDir);

    public CategoryDto createCategory(CategoryDto categoryDto);

    public CategoryDto getCategoryById(long id);
}
