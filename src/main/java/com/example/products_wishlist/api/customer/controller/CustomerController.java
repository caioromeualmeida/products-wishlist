package com.example.products_wishlist.api.customer.controller;

import java.util.Set;

import javax.validation.Valid;

import com.example.products_wishlist.api.customer.dto.CustomerDetailDto;
import com.example.products_wishlist.api.customer.dto.CustomerDto;
import com.example.products_wishlist.api.customer.service.CustomerService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    
    private CustomerService customerService;

    CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerDetailDto> create(@Valid @RequestBody CustomerDto customerDto){
        CustomerDetailDto response = customerService.create(customerDto);
        return new ResponseEntity<CustomerDetailDto>(response, HttpStatus.CREATED);
    }
    
    @GetMapping(path = "/{customerId}")
    public ResponseEntity<CustomerDetailDto> getUser(@PathVariable String customerId){
        return new ResponseEntity<CustomerDetailDto>(customerService.get(customerId), HttpStatus.OK);    
    }

    @DeleteMapping(path = "/{customerId}")
    public ResponseEntity<Void> remove(@PathVariable String customerId){
        customerService.remove(customerId);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
    
    @PutMapping(path = "/{customerId}")
    public ResponseEntity<CustomerDetailDto> updateUser(@PathVariable String customerId, @RequestBody CustomerDto customerDto){
        return new ResponseEntity<CustomerDetailDto>(customerService.update(customerId, customerDto), HttpStatus.OK);    
    }

    @GetMapping
    public ResponseEntity<Page<CustomerDetailDto>> getAll(Pageable pageable){
        Page<CustomerDetailDto> response = customerService.getAll(pageable);
        return new ResponseEntity<Page<CustomerDetailDto>>(response, HttpStatus.OK);
    }

    @PostMapping(path = "/{customerId}/products")
    public ResponseEntity<Set<String>> createProduct(@PathVariable String customerId, @Valid @RequestBody Set<String> products){
        Set<String> response = customerService.createProduct(customerId, products);
        return new ResponseEntity<Set<String>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{customerId}/products")
    public ResponseEntity<Void> removeProduct(@PathVariable String customerId, @Valid @RequestBody Set<String> products){
        customerService.removeProduct(customerId, products);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
