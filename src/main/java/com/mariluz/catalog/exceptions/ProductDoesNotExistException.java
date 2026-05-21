package com.mariluz.catalog.exceptions;

public class ProductDoesNotExistException extends RuntimeException {

    // Constructor por defecto para búsquedas por ID único
    public ProductDoesNotExistException() {
        super("No existe un producto con ese id");
    }

    // Constructor con mensaje personalizado (ej: lista de IDs no encontrados)
    public ProductDoesNotExistException(String message) {
        super(message);
    }
}
