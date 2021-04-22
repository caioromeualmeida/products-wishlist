package com.example.products_wishlist.api.customer.service;

import java.util.Set;

import com.example.products_wishlist.api.customer.dto.CustomerDetailDto;
import com.example.products_wishlist.api.customer.dto.CustomerDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
    public CustomerDetailDto get(String customerId);    
    public CustomerDetailDto create(CustomerDto customerDto);    
    public void remove(String customerId);  
    public CustomerDetailDto update(String customerId, CustomerDto customerDto);  
    public Page<CustomerDetailDto> getAll(Pageable pageable); 

    public Set<String> createProduct(String customerId, Set<String> products);
    public void removeProduct(String customerId, Set<String> products);    
}
