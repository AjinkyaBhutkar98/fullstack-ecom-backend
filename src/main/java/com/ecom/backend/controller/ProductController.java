package com.ecom.backend.controller;

import com.ecom.backend.dto.PagedResponse;
import com.ecom.backend.dto.ProductDto;
import com.ecom.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {


    @Autowired
    private ProductService productService;

    @PostMapping("/admin/add/categories/{categoryId}")
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto, @PathVariable Long categoryId){

        System.out.println(productDto+" "+categoryId);
        ProductDto productDto1=productService.addProduct(productDto,categoryId);
        return new ResponseEntity<>(productDto1, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PagedResponse<ProductDto>> getAllProducts(
            @RequestParam(name = "page",defaultValue = "0") int page,
            @RequestParam(name = "size",defaultValue = "10") int size,
            @RequestParam(name = "sortBy",defaultValue = "name") String sortBy,
            @RequestParam(name = "sortDir",defaultValue = "asc") String sortDir

    ){

        return new ResponseEntity<>(productService.getAllProducts(page,size,sortBy,sortDir),HttpStatus.OK);
    }

    //get products by category
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable Long categoryId){

        return new ResponseEntity<>(productService.getProductByCategory(categoryId),HttpStatus.OK);
    }

    //get products by keyword
    @GetMapping("/keyword/{keyword}")
    public ResponseEntity<List<ProductDto>> getProductsByKeyword(@PathVariable String keyword){

        return new ResponseEntity<>(productService.getProductsByKeyword(keyword),HttpStatus.OK);

    }

    //update product (admin api)
    @PutMapping("/update/{id}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto,@PathVariable Long id){

        ProductDto updatedProductDto=productService.updateProduct(productDto,id);

        return new ResponseEntity<>(updatedProductDto,HttpStatus.OK);

    }

}
