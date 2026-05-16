package com.mariluz.catalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class UpdateProductRequest {

    @NotNull(message = "El id del producto es obligatorio")
    private Integer id;

    @NotBlank(message = "El nombre del producto es obligatorio")
    private String name;

    @NotNull(message = "El precio del producto es obligatorio")
    private Integer price;

    @NotNull(message = "La cantidad del producto es obligatoria")
    private Integer quantity;
}
