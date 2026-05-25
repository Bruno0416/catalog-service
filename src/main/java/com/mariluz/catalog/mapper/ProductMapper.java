package com.mariluz.catalog.mapper;

import com.mariluz.catalog.dto.ProductResponse;
import com.mariluz.catalog.dto.UpdateProductRequest;
import com.mariluz.catalog.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

// Permite ignorar los valores nulos
@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ProductMapper {
    /* Dado que se ignoran los valores nulos updateProduct unicamente va a
        actualizar los valores de la entidad que si fueron ingresados en el dto
        *** (id es obligatorio para identificar a la entidad a actualizar) ***
    */
    void updateProduct(
        UpdateProductRequest request,
        @MappingTarget Product product
    );

    // Reemplazo del helper de CatalogService
    ProductResponse toResponse(Product product);
}
