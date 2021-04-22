package com.example.products_wishlist.api.customer.repository;

import com.example.products_wishlist.api.customer.model.Customer;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, String>{
    
}
