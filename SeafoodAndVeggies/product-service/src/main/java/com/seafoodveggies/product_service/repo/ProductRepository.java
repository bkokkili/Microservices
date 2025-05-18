package com.seafoodveggies.product_service.repo;

import com.seafoodveggies.product_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
