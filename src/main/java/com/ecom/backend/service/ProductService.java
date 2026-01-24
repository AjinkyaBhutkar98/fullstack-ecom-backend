package com.ecom.backend.service;

import com.ecom.backend.dto.PagedResponse;
import com.ecom.backend.dto.ProductDto;
import com.ecom.backend.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {


    public ProductDto addProduct(ProductDto productDto,Long id);

    public PagedResponse<ProductDto> getAllProducts(int page, int size, String sortBy, String sortDir);

    public List<ProductDto> getProductByCategory(Long categoryId);

    public List<ProductDto> getProductsByKeyword(String keyword);

    public ProductDto updateProduct(ProductDto productDto,Long productId);

    public ProductDto deleteProduct(Long productId);

    public ProductDto updateProductImage(Long productId, MultipartFile productImage) throws IOException;
}
