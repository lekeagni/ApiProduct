package com.example.Manage.product.mapper;

import com.example.Manage.product.dto.ProductDTO;
import com.example.Manage.product.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductDTO dto);

    ProductDTO toDTO(Product product);
}
