package com.ecom.backend.service.impl;

import com.ecom.backend.dto.CategoryDto;
import com.ecom.backend.dto.PagedResponse;
import com.ecom.backend.dto.ProductDto;
import com.ecom.backend.entity.Category;
import com.ecom.backend.entity.Product;
import com.ecom.backend.repo.CategoryRepo;
import com.ecom.backend.repo.ProductRepo;
import com.ecom.backend.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDto addProduct(ProductDto productDto, Long id) {

//        Product product=modelMapper.map(productDto,Product.class);

        Product product=new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setQuantity(productDto.getQuantity());
        product.setPrice(productDto.getPrice());
        product.setDiscount(productDto.getDiscount());
        product.setImage("default");

        Category category=categoryRepo.findById(id).orElseThrow(()->new RuntimeException("Category Not found :"+id));

        double specialPrice= product.getPrice()-((productDto.getDiscount() * 0.01) * product.getPrice());

        product.setSpecialPrice(specialPrice);

        product.setCategory(category);
        Product savedProduct=productRepo.save(product);
        return modelMapper.map(savedProduct,ProductDto.class);
    }

    @Override
    public PagedResponse<ProductDto> getAllProducts(
            int page,
            int size,
            String sortBy,
            String sortDir

    ) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> productPage = productRepo.findAll(pageable);


        Page<ProductDto> productDtoPage=productPage.map(product ->
                modelMapper.map(product, ProductDto.class));

        return PagedResponse.fromPage(productDtoPage);
    }
}
