package com.board.board.config.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class BadRequestExection extends RuntimeException{
    public BadRequestExection(String message) {
        super(message);
    }
}
