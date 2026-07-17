package com.example.interview_os.exception;

public class EmailAlreadyExistsException extends RuntimeException{
    public EmailAlreadyExistsException(String email){
        super("Email already registered: "+email);
    }
}

