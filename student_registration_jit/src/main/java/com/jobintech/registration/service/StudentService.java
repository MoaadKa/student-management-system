package com.jobintech.registration.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jobintech.registration.dto.StudentDto;
import com.jobintech.registration.dto.StudentResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface StudentService {
    StudentResponse getAllStudents(int page, int size, String sortBy, String sortDir);
    StudentDto getStudent(Long id);
    CompletableFuture<StudentDto> createStudent(StudentDto studentDto, MultipartFile cv);
    StudentDto updateStudent(Long id, StudentDto studentDto) throws JsonProcessingException;
    StudentDto deleteStudent(Long id);
    ResponseEntity<Resource> downloadStudentCv(String cvName) throws IOException;

}
