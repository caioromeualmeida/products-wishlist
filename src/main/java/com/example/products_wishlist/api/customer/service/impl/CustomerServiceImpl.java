package com.example.products_wishlist.api.customer.service.impl;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import com.example.products_wishlist.api.customer.dto.CustomerDetailDto;
import com.example.products_wishlist.api.customer.dto.CustomerDto;
import com.example.products_wishlist.api.customer.exception.CustomerNotFoundException;
import com.example.products_wishlist.api.customer.mapper.CustomerMapper;
import com.example.products_wishlist.api.customer.model.Customer;
import com.example.products_wishlist.api.customer.repository.CustomerRepository;
import com.example.products_wishlist.api.customer.service.CustomerService;
import com.example.products_wishlist.api.product.dto.ProductDto;
import com.example.products_wishlist.api.product.exception.ProductExistException;
import com.example.products_wishlist.api.product.exception.ProductNotFoundException;
import com.example.products_wishlist.shared.webservices.RestWebServices;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;
    private RestWebServices restWebServices;

    CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper, RestWebServices restWebServices){
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.restWebServices = restWebServices;
    }

    @Override
    public CustomerDetailDto create(CustomerDto customerDto) throws ProductNotFoundException, DuplicateKeyException {
        Customer customer = customerRepository.save(customerMapper.fromDto(customerDto));
        return customerMapper.toDetailDto(customer);
    }

    @Override
    public CustomerDetailDto get(String customerId) throws ProductNotFoundException {
        try {
            Optional<Customer> customer = customerRepository.findById(customerId);
            return customerMapper.toDetailDto(customer.get());
        } catch (NoSuchElementException e) {
            throw new CustomerNotFoundException("Customer_id " + customerId + " not found");
        }
    }

    @Override
    public void remove(String customerId) {
        try {
            Optional<Customer> customer = customerRepository.findById(customerId);    
            customerRepository.delete(customer.get());
        } catch (NoSuchElementException e) {
            throw new CustomerNotFoundException("Customer_id " + customerId + " not found");
        }
    }

    @Override
    public CustomerDetailDto update(String customerId, CustomerDto customerDto) {
        try {
            Optional<Customer> customer = customerRepository.findById(customerId);    
            customer.get().setName(customerDto.getName());
            return customerMapper.toDetailDto(customerRepository.save(customer.get()));
        } catch (NoSuchElementException e) {
            throw new CustomerNotFoundException("Customer_id " + customerId + " not found");
        }
    }

    @Override
    public Page<CustomerDetailDto> getAll(Pageable pageable) throws ProductNotFoundException {
        Page<Customer> customers = customerRepository.findAll(pageable);
        List<CustomerDetailDto> customersDto = customerMapper.toDetailsDto(customers.getContent());    
        return new PageImpl<CustomerDetailDto>(customersDto, pageable, customers.getTotalElements());
    }


    @Override
    public Set<String> createProduct(String customerId, Set<String> products) {
        try {
            Optional<Customer> customer = customerRepository.findById(customerId);
            Set<String> validatedProducts = new LinkedHashSet<>();

            for(String product : products){
                ProductDto newProductDto = restWebServices.loadProductFromApi(product);
                validatedProducts.add(newProductDto.getId());
            }
            
            if(customer.get().getProducts() == null){
                customer.get().setProducts(validatedProducts);
            }else{
                //valida se não há algum produto já cadastrado
                if(!Collections.disjoint(customer.get().getProducts(), validatedProducts)){
                    throw new ProductExistException("Product has already been added.");
                }else{
                    customer.get().getProducts().addAll(validatedProducts);
                }
            }

            customerRepository.save(customer.get());
            return validatedProducts;
        } catch (NoSuchElementException e) {
            throw new CustomerNotFoundException("Customer_id " + customerId + " not found");
        }
    }


    public void removeProduct(String customerId, Set<String> products) {
        try {
            Optional<Customer> customer = customerRepository.findById(customerId);

            for(String product : products){
                customer.get().getProducts().remove(product);
                customerRepository.save(customer.get());
            }

        } catch (NoSuchElementException e) {
            throw new CustomerNotFoundException("Customer_id " + customerId + " not found");
        }
    }
}
