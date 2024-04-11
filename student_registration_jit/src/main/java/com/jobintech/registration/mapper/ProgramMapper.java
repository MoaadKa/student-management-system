package com.jobintech.registration.mapper;

import com.jobintech.registration.dto.ProgramDto;
import com.jobintech.registration.entity.Program;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProgramMapper implements MapperInterface<Program, ProgramDto> {

    private final ModelMapper mapper;

    public ProgramMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Program mapToEntity(ProgramDto dto) {
        return mapper.map(dto, Program.class);
    }

    @Override
    public ProgramDto mapToDto(Program program) {
        return mapper.map(program, ProgramDto.class);
    }
}
