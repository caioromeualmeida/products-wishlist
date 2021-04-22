package com.example.products_wishlist.api.customer.model;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.Data;

@Data
@Document
public class Customer {
    @MongoId
    private String id = UUID.randomUUID().toString();

    @Indexed(unique = true)
    private String email;

    private String name; 

    private Set<String> products;

    public Customer(String email, String name, Set<String> products){
        this.email = email;
        this.name = name;
        this.products = products;
    }
}
