package com.ecom.backend.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryDto {

    private long id;

    @NotBlank(message = "category name should not be null")
    private String name;
}
