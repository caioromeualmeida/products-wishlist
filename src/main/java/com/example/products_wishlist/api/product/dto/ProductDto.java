package com.example.products_wishlist.api.product.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ProductDto implements Serializable {
    private Double price;
    private String image;
    private String brand;
    private String id;
    private String title;
    private String reviewScore;

    public ProductDto(String id){
        this.id = id;
    }
}
