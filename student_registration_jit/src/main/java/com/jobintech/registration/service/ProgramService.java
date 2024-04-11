package com.jobintech.registration.service;

import com.jobintech.registration.dto.ProgramDto;
import com.jobintech.registration.payload.ProgramResponse;

import java.util.List;

public interface ProgramService {

    List<ProgramDto> getPrograms(int pageNumber, int pageSize);

    ProgramResponse getProgramPage(int pageNumber, int pageSize);

    ProgramDto getProgramById(Long id);

    ProgramDto createNewProgram(ProgramDto programDto);

    ProgramDto updateProgram(Long id, ProgramDto programDto);

    ProgramDto deleteProgram(Long id);


}
