package com.example.products_wishlist.api.customer.dto;

import java.util.Set;

import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDto {
    @Email(message = "Invalid e-mail.")
    private String email;
    private String name;
    
    private Set<String> products;
}
