package com.petshop.petshop.exception;

import org.springframework.stereotype.Component;

@Component
public class ProductResponseMessageProvider implements ResponseMessageProvider {
    @Override
    public String getSuccessCreateMessage() { return "Product created successfully"; }
    @Override
    public String getSuccessUpdateMessage() { return "Product updated successfully"; }
    @Override
    public String getSuccessDeleteMessage() { return "Product deleted successfully"; }
    @Override
    public String getSuccessRetrieveMessage() { return "Product retrieved successfully"; }
    @Override
    public String getSuccessListMessage() { return "Products retrieved successfully"; }
}
