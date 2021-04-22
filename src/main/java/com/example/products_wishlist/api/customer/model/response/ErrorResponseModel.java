package com.example.products_wishlist.api.customer.model.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseModel {
    private Date timestamp;
    private String message;
}