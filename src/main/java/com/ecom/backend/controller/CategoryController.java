package com.ecom.backend.controller;


import com.ecom.backend.dto.CategoryDto;
import com.ecom.backend.dto.PagedResponse;
import com.ecom.backend.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/api/v1/all")
    public ResponseEntity<PagedResponse<CategoryDto>> getAll(
            @RequestParam(name = "page",defaultValue = "0") int page,
            @RequestParam(name = "size",defaultValue = "10") int size,
            @RequestParam(name = "sortBy",defaultValue = "name") String sortBy,
            @RequestParam(name = "sortDir",defaultValue = "asc") String sortDir

    ){

        return new ResponseEntity<>(categoryService.getAllCategories(page,size,sortBy,sortDir), HttpStatus.OK);

    }

    @GetMapping("/echo")
    //http://localhost:8080/categories/echo?message=ajinkya
    public ResponseEntity<String> echoMessage(@RequestParam(name = "message",defaultValue = "none",required = true) String msg){

        return new ResponseEntity<>("echoed msg: "+msg, HttpStatus.OK);
    }

    @PostMapping("/api/v1/create")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){

        CategoryDto savedCategoryDto=categoryService.createCategory(categoryDto);

        return new ResponseEntity<>(savedCategoryDto,HttpStatus.CREATED);

    }

    @GetMapping("/api/v1/get/{id}")
    public ResponseEntity<CategoryDto> getById(@PathVariable long id){

        return new ResponseEntity<>(categoryService.getCategoryById(id), HttpStatus.OK);

    }


}
