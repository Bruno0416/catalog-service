package com.mariluz.catalog.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class ProductsByIdRequest {

    @NotNull(message = "La lista de IDs es obligatoria")
    @NotEmpty(message = "La lista de IDs no puede estar vacía")
    List<@Positive(message = "Cada ID debe ser mayor que 0") Integer> ids;
}
