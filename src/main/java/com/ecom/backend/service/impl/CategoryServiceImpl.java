package com.ecom.backend.service.impl;


import com.ecom.backend.dto.CategoryDto;
import com.ecom.backend.entity.Category;
import com.ecom.backend.exceptions.ResourceNotFoundException;
import com.ecom.backend.repo.CategoryRepo;
import com.ecom.backend.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;




    @Override
    public List<CategoryDto> getAllCategories() {

        List<Category> allCateg=categoryRepo.findAll();

        if(allCateg.isEmpty()){
            throw new ResourceNotFoundException("No categories found!!");
        }

        return allCateg.stream().map(x->modelMapper.map(x, CategoryDto.class)).toList();

    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        Category findCategory=categoryRepo.findByName(categoryDto.getName());
        if(findCategory!=null){
            throw new ResourceNotFoundException("Category already exists with name: "+ findCategory.getName());
        }
        Category category=new Category();
        category.setName(categoryDto.getName());
        Category savedCategory=categoryRepo.save(category);


        return modelMapper.map(savedCategory,CategoryDto.class);
    }

    @Override
    public CategoryDto getCategoryById(long id) {

        Category category=categoryRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Category not found with id: "+id));



        return modelMapper.map(category,CategoryDto.class);
    }
}
