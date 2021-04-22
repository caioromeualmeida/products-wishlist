package com.example.products_wishlist.api.customer.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.example.products_wishlist.api.customer.dto.CustomerDetailDto;
import com.example.products_wishlist.api.customer.dto.CustomerDto;
import com.example.products_wishlist.api.customer.model.Customer;
import com.example.products_wishlist.api.product.dto.ProductDto;
import com.example.products_wishlist.api.product.exception.ProductNotFoundException;
import com.example.products_wishlist.shared.webservices.RestWebServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class CustomerMapperDecorator implements CustomerMapper {
   
    @Autowired
    private RestWebServices restWebServices;

    @Autowired
    @Qualifier("delegate")
    private CustomerMapper delegate;

    @Override
    public CustomerDto toDto(Customer customer){
        return delegate.toDto(customer);
    }
    
    @Override
    public Customer fromDto(CustomerDto customerDto){
        //checa se os produtos existem
        if(customerDto.getProducts() != null){
            for(String product : customerDto.getProducts()){
                restWebServices.loadProductFromApi(product);
            }
        }
        
        return new Customer(customerDto.getEmail(), customerDto.getName(), customerDto.getProducts());
    }

    @Override
    public CustomerDetailDto toDetailDto(Customer customer) throws ProductNotFoundException {
        List<ProductDto> products = new ArrayList<>();

        if(customer.getProducts() != null){
            for(String product : customer.getProducts()){
                products.add(restWebServices.loadProductFromApi(product));
            }
        }

        return new CustomerDetailDto(customer.getId(), customer.getEmail(), customer.getName(), products);
    }

    @Override
    public List<CustomerDetailDto> toDetailsDto(List<Customer> customers) throws ProductNotFoundException {
        List<CustomerDetailDto> customersDetails = new ArrayList<>();

        for(Customer customer : Optional.ofNullable(customers).orElse(Collections.emptyList())){
            List<ProductDto> products = new ArrayList<>();
         
            if(customer.getProducts() != null){
                for(String product: customer.getProducts()){
                    products.add(restWebServices.loadProductFromApi(product));
                }
            }

            customersDetails.add(new CustomerDetailDto(customer.getId(), customer.getEmail(), customer.getName(), products));
        }

        return customersDetails;
    }
}
