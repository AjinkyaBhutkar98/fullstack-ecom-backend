package com.ecom.backend.service;

import com.ecom.backend.dto.PagedResponse;
import com.ecom.backend.dto.ProductDto;
import com.ecom.backend.entity.Product;

import java.util.List;

public interface ProductService {


    public ProductDto addProduct(ProductDto productDto,Long id);

    public PagedResponse<ProductDto> getAllProducts(int page, int size, String sortBy, String sortDir);

    public List<ProductDto> getProductByCategory(Long categoryId);

    public List<ProductDto> getProductsByKeyword(String keyword);

    public ProductDto updateProduct(ProductDto productDto,Long productId);
}
