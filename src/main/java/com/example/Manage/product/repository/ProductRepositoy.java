package com.example.Manage.product.repository;

import com.example.Manage.product.dto.ProductDTO;
import com.example.Manage.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepositoy extends JpaRepository<Product, Integer> {
    Optional<Product> findByName(String name);
}
