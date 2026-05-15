package com.mariluz.catalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CreateProductRequest {

    @NotBlank(message = "El nombre del producto es obligatorio")
    private String name;

    @NotBlank(message = "La descripción del producto es obligatoria")
    private String description;

    @NotNull(message = "El precio del producto es obligatorio")
    @Positive(message = "El precio del producto debe ser mayor que 0")
    private Integer price;

    @NotNull(message = "El stock del producto es obligatorio")
    @Positive(message = "El stock del producto debe ser mayor que 0")
    private Integer stock;
}
