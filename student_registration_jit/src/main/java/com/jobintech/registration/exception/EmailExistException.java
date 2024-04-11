package com.jobintech.registration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class EmailExistException extends RuntimeException{
    private final String email;
    public EmailExistException(String email){
        super(String.format("%s already registered in jobInTech",email));
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
