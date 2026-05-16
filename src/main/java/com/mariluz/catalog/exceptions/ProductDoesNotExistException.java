package com.mariluz.catalog.exceptions;

public class ProductDoesNotExistException extends RuntimeException {

    public ProductDoesNotExistException() {
        super("Product does not exist");
    }
}
