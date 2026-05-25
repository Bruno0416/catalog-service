package com.mariluz.catalog.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateProductRequest {

    @NotNull(message = "El id del producto es obligatorio")
    @Positive(message = "El id del producto debe ser mayor que 0")
    private Integer id;

    // Opcional pero no puede ir vacio
    @Size(min = 1, message = "El nombre no puede estar vacío si se envía")
    private String name;

    @Positive(message = "El precio del producto debe ser mayor que 0")
    private Integer price;

    @PositiveOrZero(message = "La cantidad no puede ser negativa")
    private Integer quantity;
}
