package com.springboot.project.techno_shop.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiException {

    public NotFoundException(String resourceName, Long id) {
        super(HttpStatus.NOT_FOUND, String.format("%s with id %d not found", resourceName, id));
    }
}
