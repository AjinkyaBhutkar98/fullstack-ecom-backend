package com.ecom.backend.repo;

import com.ecom.backend.entity.Category;
import com.ecom.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {


    //select * from products where category_id=1 order by price asc;
    List<Product> findByCategoryOrderByPriceAsc(Category category);

    List<Product> findByNameContainingIgnoreCase(String keyword);

    boolean existsByNameAndCategory(String name, Category category);
}
