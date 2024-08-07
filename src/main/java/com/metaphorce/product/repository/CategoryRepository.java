package com.metaphorce.product.repository;

import com.metaphorce.commonslib.entities.Category;
import com.metaphorce.commonslib.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Repository
    interface ProductRepository extends JpaRepository<Product, Long> {
    }
}
