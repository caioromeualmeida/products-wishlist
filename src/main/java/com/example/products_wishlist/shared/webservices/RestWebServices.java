package com.example.products_wishlist.shared.webservices;

import com.example.products_wishlist.api.product.dto.ProductDto;
import com.example.products_wishlist.api.product.exception.ProductNotFoundException;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestWebServices {

    @Cacheable("loadProduct")
    public ProductDto loadProductFromApi(String id) throws ProductNotFoundException {
        System.out.println("Chamou!");
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://challenge-api.luizalabs.com/api/product/" + id + "/";

        try {
            ResponseEntity<ProductDto> response = restTemplate.getForEntity(url, ProductDto.class);
            return response.getBody();
        } catch (Exception e) {
            throw new ProductNotFoundException("Product_id " + id + " not found");
        }
    }
}