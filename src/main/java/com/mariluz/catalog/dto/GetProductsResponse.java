package com.mariluz.catalog.dto;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetProductsResponse {

    List<ProductResponse> products;
}
