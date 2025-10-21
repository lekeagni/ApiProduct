package com.example.Manage.product.exception;

public class ProductAlreadyExistException extends RuntimeException {
    public ProductAlreadyExistException(String name) {

        super("product already exist with " + name);
    }
}
