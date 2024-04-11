package com.jobintech.registration.service.imp;

import com.jobintech.registration.exception.FileExceptionHandler;
import com.jobintech.registration.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public void uploadFile(MultipartFile file,String name){
        createDirectoryIfNotExists(DIRECTORY);
        String fileName = name + ".pdf";
        Path fileStorage = get(DIRECTORY,fileName).toAbsolutePath().normalize();
        try {
            copy(file.getInputStream(),fileStorage,REPLACE_EXISTING);
        } catch (IOException e) {
            throw new FileExceptionHandler(fileName);
        }
    }

    @Override
    public ResponseEntity<Resource> downloadFile(String name) throws IOException {
        Path filePath = get(DIRECTORY).toAbsolutePath().normalize().resolve(name+".pdf");
        if(!Files.exists(filePath)){
            throw new FileNotFoundException(name + "not found");
        }
        Resource resource = new UrlResource(filePath.toUri());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Filename",name);
        headers.add(HttpHeaders.CONTENT_DISPOSITION,"attachment;File-Name="+name);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(filePath))).headers(headers).body(resource);
    }

    @Override
    public void createDirectoryIfNotExists(String directory){
        Path path = Paths.get(directory).toAbsolutePath().normalize();
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
