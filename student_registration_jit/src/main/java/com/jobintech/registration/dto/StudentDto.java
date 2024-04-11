package com.jobintech.registration.dto;

import com.jobintech.registration.enums.Gender;
import com.jobintech.registration.enums.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class StudentDto {

    private Long id;

    @NotNull
    @NotEmpty
    @Size(min = 2,message = "First name should be more than 2 characters")
    private String firstName;

    @NotNull
    @NotEmpty
    @Size(min = 2,message = "Last name should be more than 2 characters")
    private String lastName;

    @NotEmpty
    @NotNull
    @Email
    private String email;

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    @NotEmpty
    private String city;

    @NotNull
    private Gender gender;

    @NotNull
    private ProgramDto programDto;

    @NotNull
    private Status status = Status.PENDING;

    @NotNull
    private String imgUrl;

    private String cvName;
}
