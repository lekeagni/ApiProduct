package com.example.Manage.product.service.ServiceImpl;

import com.example.Manage.product.dto.ProductDTO;
import com.example.Manage.product.exception.ProductAlreadyExistException;
import com.example.Manage.product.exception.ProductNotFoundException;
import com.example.Manage.product.mapper.ProductMapper;
import com.example.Manage.product.model.Product;
import com.example.Manage.product.repository.ProductRepositoy;
import com.example.Manage.product.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepositoy productRepositoy;
    private final ProductMapper productMapper;


    public ProductServiceImpl(ProductRepositoy productRepositoy, ProductMapper productMapper) {
        this.productRepositoy = productRepositoy;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDTO createProduct(ProductDTO dto) {

        Optional<Product> found = this.productRepositoy.findByName(dto.getName());
        if (found.isPresent()){
            throw new ProductAlreadyExistException(dto.getName());
        }
        Product product = productMapper.toEntity(dto);
        return productMapper.toDTO(this.productRepositoy.save(product));
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> found = this.productRepositoy.findAll();
        return found.stream().map(productMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(int product_id) {
        Product product = this.productRepositoy.findById(product_id).orElseThrow(()-> new ProductNotFoundException(product_id));
        return productMapper.toDTO(product);

    }

    @Override
    public ProductDTO updateProduct(int product_id, ProductDTO dto) {
        Product existing = this.productRepositoy.findById(product_id)
                .orElseThrow(()-> new ProductNotFoundException(product_id));
        existing.setName(dto.getName());
        existing.setCategorie(dto.getCategorie());
        existing.setPrix(dto.getPrix());

        return productMapper.toDTO(this.productRepositoy.save(existing));
    }

    @Override
    public Boolean deleteProduct(int product_id) {
Optional<Product> found = this.productRepositoy.findById(product_id);
if (found.isPresent()){
    Product product = found.get();
    this.productRepositoy.delete(product);
    return true;
}
        return false;
    }
}
