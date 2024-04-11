package com.jobintech.registration.repository;

import com.jobintech.registration.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,Long> {
    Optional<Student> findStudentById(Long id);
    Optional<Student> findStudentByEmail(String email);
}
