package com.example.products_wishlist.api.product.exception;

public class ProductNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    
    public ProductNotFoundException(String message){
        super(message);
    }
}
