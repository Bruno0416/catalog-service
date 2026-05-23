package com.mariluz.catalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class CreateProductRequest {

    @NotBlank(message = "El nombre del producto es obligatorio")
    private String name;

    @NotNull(message = "El precio del producto es obligatorio")
    @Positive(message = "El precio del producto debe ser mayor que 0")
    private Integer price;

    @NotNull(message = "El stock del producto es obligatorio")
    @PositiveOrZero(message = "El stock del producto no puede ser negativo") // M4: consistente con la entidad
    private Integer quantity;
}
