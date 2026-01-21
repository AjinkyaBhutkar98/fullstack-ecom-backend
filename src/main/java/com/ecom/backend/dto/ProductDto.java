package com.ecom.backend.dto;

import com.ecom.backend.entity.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private String name;
    private String description;
    private Integer quantity;
    private Double price;
    private String image;
    private Double discount;
    private Double specialPrice;
    private Category category;
}
