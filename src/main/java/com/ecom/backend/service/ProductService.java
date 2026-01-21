package com.ecom.backend.service;

import com.ecom.backend.dto.PagedResponse;
import com.ecom.backend.dto.ProductDto;

public interface ProductService {


    public ProductDto addProduct(ProductDto productDto,Long id);

    public PagedResponse<ProductDto> getAllProducts(int page, int size, String sortBy, String sortDir);
}
