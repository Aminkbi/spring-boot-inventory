package com.aminkbi.springbootInventory.exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String notFound) {
        super(notFound);
    }
}
