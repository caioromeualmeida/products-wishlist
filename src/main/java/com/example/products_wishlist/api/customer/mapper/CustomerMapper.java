package com.example.products_wishlist.api.customer.mapper;

import java.util.List;

import com.example.products_wishlist.api.customer.dto.CustomerDetailDto;
import com.example.products_wishlist.api.customer.dto.CustomerDto;
import com.example.products_wishlist.api.customer.model.Customer;
import com.example.products_wishlist.api.product.exception.ProductNotFoundException;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel="spring")
@DecoratedWith(CustomerMapperDecorator.class)
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper( CustomerMapper.class );

    CustomerDto toDto(Customer customer);
    
    default Customer fromDto(CustomerDto customerDto) throws ProductNotFoundException {
        return null;
    }

    default CustomerDetailDto toDetailDto(Customer customer) throws ProductNotFoundException {
        return null;
    }

    default List<CustomerDetailDto> toDetailsDto(List<Customer> customers) throws ProductNotFoundException {
        return null;
    }
}
