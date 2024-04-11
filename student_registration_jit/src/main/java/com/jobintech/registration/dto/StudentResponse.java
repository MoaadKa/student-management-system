package com.jobintech.registration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class StudentResponse {
    private List<StudentDto> content;
    private int page;
    private int size;
    private Long totalElement;
    private int totalPage;
    private boolean last;
}
