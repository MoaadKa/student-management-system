package com.jobintech.registration.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;


@Data
@RequiredArgsConstructor
public class UserMessage implements Serializable {
    private String lastName;
    private String firstName;
    private String email;
}
