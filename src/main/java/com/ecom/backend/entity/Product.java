package com.ecom.backend.entity;

import jakarta.persistence.*;
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
    private String name;

    @Column
    private String description;

    private String image;

    @Column
    private Integer quantity;

    private double price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private double specialPrice;

    private double discount;


}
