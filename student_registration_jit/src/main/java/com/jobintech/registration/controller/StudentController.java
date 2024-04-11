package com.jobintech.registration.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jobintech.registration.dto.StudentDto;
import com.jobintech.registration.dto.StudentResponse;
import com.jobintech.registration.service.StudentService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@CrossOrigin
@RequestMapping(path = "api/v1/students")
public class StudentController {
    private final StudentService studentService;


    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(path = "all")
    public StudentResponse getAllStudents(
            @RequestParam(defaultValue = "0",required = false) int page,
            @RequestParam(defaultValue = "5",required = false) int size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDir

            ){
        return studentService.getAllStudents(page, size, sortBy, sortDir);
    }
    @GetMapping(path = "{id}")
    public ResponseEntity<StudentDto> getStudent(@PathVariable Long id){
        return new ResponseEntity<>(studentService.getStudent(id),HttpStatus.OK);
    }
    @PostMapping(path = "add")
    public ResponseEntity<CompletableFuture<StudentDto>> createStudent(@Valid @ModelAttribute StudentDto studentDto, @RequestParam("cvFile") MultipartFile cvFile){
        return new ResponseEntity<>(studentService.createStudent(studentDto,cvFile),HttpStatus.CREATED);
    }
    @PutMapping(path = "update/{id}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentDto studentDto) throws JsonProcessingException {
        return new ResponseEntity<>(studentService.updateStudent(id, studentDto),HttpStatus.OK);
    }
    @DeleteMapping(path = "delete/{id}")
    public ResponseEntity<StudentDto> deleteStudent(@PathVariable Long id){
        return new ResponseEntity<>(studentService.deleteStudent(id),HttpStatus.OK);
    }
    @GetMapping(path = "downloadCV/{cvName}")
    public ResponseEntity<Resource> downloadStudentCv(@PathVariable String cvName) throws IOException {
        return this.studentService.downloadStudentCv(cvName);
    }
}
