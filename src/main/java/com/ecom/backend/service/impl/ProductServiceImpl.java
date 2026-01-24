package com.ecom.backend.service.impl;

import com.ecom.backend.dto.CategoryDto;
import com.ecom.backend.dto.PagedResponse;
import com.ecom.backend.dto.ProductDto;
import com.ecom.backend.entity.Category;
import com.ecom.backend.entity.Product;
import com.ecom.backend.exceptions.ResourceNotFoundException;
import com.ecom.backend.repo.CategoryRepo;
import com.ecom.backend.repo.ProductRepo;
import com.ecom.backend.service.FileUploadService;
import com.ecom.backend.service.ProductService;
import jakarta.validation.constraints.Null;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileUploadService fileUploadService;

    @Override
    public ProductDto addProduct(ProductDto productDto, Long id) {

        Category category=categoryRepo.findById(id).orElseThrow(()->new RuntimeException("Category Not found :"+id));

        boolean exists = productRepo.existsByNameAndCategory(
                productDto.getName(),
                category
        );

        if (exists) {
            throw new ResourceNotFoundException("Product already exists !!");
        }

        Product product=new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setQuantity(productDto.getQuantity());
        product.setPrice(productDto.getPrice());
        product.setDiscount(productDto.getDiscount());
        product.setImage("default");

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

    @Override
    public List<ProductDto> getProductByCategory(Long categoryId) {

        Category category=categoryRepo.findById(categoryId).orElseThrow(()->new RuntimeException("Category Not found :"+categoryId));

        List<Product> getProductsListByCategory=productRepo.findByCategoryOrderByPriceAsc(category);

        if(getProductsListByCategory.isEmpty()){
            throw new ResourceNotFoundException("No products found with category containing :"+category.getName());
        }

        return getProductsListByCategory.stream().map(product->modelMapper.map(product,ProductDto.class)).toList();
    }

    @Override
    public List<ProductDto> getProductsByKeyword(String keyword) {

        List<Product> getProductsListByKeyword=productRepo.findByNameContainingIgnoreCase(keyword);

        if(getProductsListByKeyword.isEmpty()){
            throw new ResourceNotFoundException("No products found with keyword containing :"+keyword);
        }

        return getProductsListByKeyword.stream().map(product->modelMapper.map(product,ProductDto.class)).toList();
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, Long productId) {

        Product product=productRepo.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product not found : "+productId));

        modelMapper.map(productDto,product);

        Product updatedProduct=productRepo.save(product);

        return modelMapper.map(updatedProduct, ProductDto.class);
    }

    @Override
    public ProductDto deleteProduct(Long productId) {

        Product product=productRepo.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product not found:"+productId));

        ProductDto productDto=modelMapper.map(product,ProductDto.class);

        productRepo.delete(product);

        return productDto;
    }

    @Override
    public ProductDto updateProductImage(Long productId, MultipartFile productImage) throws IOException {

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + productId));

        String path = System.getProperty("user.dir") + "/uploads/images";

        String fileName = fileUploadService.uploadImage(path, productImage);

        product.setImage(fileName);

        productRepo.save(product);

        return modelMapper.map(product, ProductDto.class);
    }





//    private String uploadImage(String path,MultipartFile productImage) throws IOException {
//
//        //original file name
//        String originalFileName=productImage.getOriginalFilename();
//
//        //generate a unique file name
//        String randomId= UUID.randomUUID().toString();
//        String updatedFileName=randomId.concat(originalFileName.substring(originalFileName.lastIndexOf('.'))); //if ayb.jpg -->1234 1234.jpg
//
//        //File.pathSeparator represents / to support on all operating systems
//        String filePath=path+ File.separator+updatedFileName;
//
//        //check if path exist or create
////        File folder=new File(path);
////        if(!folder.exists()){
////            folder.mkdir();
////        }
//
//        if(!Files.exists(Paths.get(path))){
//            //creating folder if does not exist
//            System.out.println("Creating folder");
//            Files.createDirectories(Paths.get(path));
//        }
//
//
//        //upload to server
//        Files.copy(productImage.getInputStream(), Paths.get(filePath));
//
//
//        //return file name
//        return updatedFileName;
//    }


}
