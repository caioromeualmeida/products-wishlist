package com.example.products_wishlist.api.customer.model.response;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorListResponseModel {
    private Date timestamp;
    private List<String> message;
}