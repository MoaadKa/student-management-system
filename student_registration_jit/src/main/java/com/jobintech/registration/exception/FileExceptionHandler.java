package com.jobintech.registration.exception;

public class FileExceptionHandler  extends RuntimeException{
    private final String file;
    public FileExceptionHandler(String file){
        super(String.format("there's an error with the file: ",file));
        this.file = file;
    }
    public String getEmail() {
        return file;
    }
}
