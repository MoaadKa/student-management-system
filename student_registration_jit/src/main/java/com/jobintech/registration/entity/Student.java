package com.jobintech.registration.entity;

import com.jobintech.registration.enums.Gender;
import com.jobintech.registration.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@Table
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false
            ,unique = true)
    private String email;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @ManyToOne(targetEntity = Program.class
            ,fetch = FetchType.EAGER)
    private Program program;

    @Column
    @Enumerated(value=EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    private String imgUrl;

    @Column(nullable = false)
    private String cvName;
}
