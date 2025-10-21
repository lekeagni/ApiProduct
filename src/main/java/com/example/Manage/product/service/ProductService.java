package com.example.Manage.product.service;

import com.example.Manage.product.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    public ProductDTO createProduct(ProductDTO dto);

    public List<ProductDTO> getAllProducts();

    public ProductDTO getProductById(int product_id);

    public ProductDTO updateProduct(int product_id, ProductDTO dto);

    public Boolean deleteProduct(int product_id);

}
