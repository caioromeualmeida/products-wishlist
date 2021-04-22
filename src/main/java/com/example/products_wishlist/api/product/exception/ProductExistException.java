package com.example.products_wishlist.api.product.exception;

public class ProductExistException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    
    public ProductExistException(String message){
        super(message);
    }
}
