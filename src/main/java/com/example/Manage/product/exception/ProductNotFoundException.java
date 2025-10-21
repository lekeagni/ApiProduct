package com.example.Manage.product.exception;

public class ProductNotFoundException extends CustomException {
    public ProductNotFoundException(int product_id) {

        super("Product not found with " + product_id);
    }
}
