package com.example.products_wishlist.api.customer.dto;

import java.util.List;

import com.example.products_wishlist.api.product.dto.ProductDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class CustomerDetailDto {
    private String id;
    private String email;
    private String name; 
    private List<ProductDto> products;
}
