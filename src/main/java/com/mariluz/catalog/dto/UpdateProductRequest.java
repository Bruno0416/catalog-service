package com.mariluz.catalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class UpdateProductRequest {

    @NotNull(message = "El id del producto es obligatorio")
    @Positive(message = "El id del producto debe ser mayor que 0")
    private Integer id;

    @NotBlank(message = "El nombre del producto es obligatorio")
    private String name;

    @NotNull(message = "El precio del producto es obligatorio")
    @Positive(message = "El precio del producto debe ser mayor que 0")
    private Integer price;

    @NotNull(message = "La cantidad del producto es obligatoria")
    @PositiveOrZero(message = "La cantidad no puede ser negativa")
    private Integer quantity;
}
