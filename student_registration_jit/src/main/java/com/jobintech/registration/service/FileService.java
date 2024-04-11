package com.jobintech.registration.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String DIRECTORY = System.getProperty("user.home") + "/Uploads";
    void uploadFile(MultipartFile file,String name);
    ResponseEntity<Resource> downloadFile(String name) throws IOException;
    void createDirectoryIfNotExists(String directory);
}