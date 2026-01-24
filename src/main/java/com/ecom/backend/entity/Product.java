package com.ecom.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true,nullable = false)
    @Size(min = 3,message = "Product size should be min 3 characters")
    private String name;

    @Column
    @NotBlank(message = "please give some description")
    private String description;

    private String image;

    @Column
    @NotNull(message = "please add some quantity in stock")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    private double price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private double specialPrice;

    private double discount;


}
