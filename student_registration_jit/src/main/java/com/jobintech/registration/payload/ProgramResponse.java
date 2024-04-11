package com.jobintech.registration.payload;

import com.jobintech.registration.dto.ProgramDto;
import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProgramResponse {

    private List<ProgramDto> content;
    private int pageNumber;
    private int pageSize;
    private int totalElements;
    private int totalPages;
}

