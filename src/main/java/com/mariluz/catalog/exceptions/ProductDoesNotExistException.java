package com.mariluz.catalog.exceptions;

public class ProductDoesNotExistException extends RuntimeException {

    public ProductDoesNotExistException() {
        super("No existe un producto con ese id");
    }
}
