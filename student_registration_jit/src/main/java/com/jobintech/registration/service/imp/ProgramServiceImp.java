package com.jobintech.registration.service.imp;

import com.jobintech.registration.dto.ProgramDto;
import com.jobintech.registration.entity.Program;
import com.jobintech.registration.exception.ResourceNotFoundException;
import com.jobintech.registration.mapper.ProgramMapper;
import com.jobintech.registration.payload.ProgramResponse;
import com.jobintech.registration.repository.ProgramRepository;
import com.jobintech.registration.service.ProgramService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProgramServiceImp implements ProgramService {

    private final ProgramRepository programRepository;
    private final ProgramMapper mapper;

    public ProgramServiceImp(ProgramRepository programRepository, ProgramMapper mapper) {
        this.programRepository = programRepository;
        this.mapper = mapper;
    }

    @Override
    public List<ProgramDto> getPrograms(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Program> programsPage = programRepository.findAll(pageable).getContent();

        return mapper.mapToDto(programsPage);
    }

    @Override
    public ProgramResponse getProgramPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Program> allPrograms = programRepository.findAll(pageable);
        Page<ProgramDto> p = allPrograms.map(mapper::mapToDto);
        List<Program> content = allPrograms.getContent();
        List<ProgramDto> programDtos = content
                .stream()
                .map(program -> mapper.mapToDto(program))
                .collect(Collectors.toList());
        ProgramResponse programResponse = new ProgramResponse();
        programResponse.setContent(programDtos);
        programResponse.setPageNumber(allPrograms.getNumber());
        programResponse.setPageSize(allPrograms.getSize());
        programResponse.setTotalPages(allPrograms.getTotalPages());
        programResponse.setTotalElements(allPrograms.getNumberOfElements());

        return programResponse;
    }


    @Override
    public ProgramDto getProgramById(Long id) {
        Program program = programRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Program", "id", String.valueOf(id)));

        return mapper.mapToDto(program);
    }

    @Override
    public ProgramDto createNewProgram(ProgramDto programDto) {
        Program program = mapper.mapToEntity(programDto);
        Program savedProgram = programRepository.save(program);
        return mapper.mapToDto(savedProgram);
    }

    @Override
    public ProgramDto updateProgram(Long id, ProgramDto programDto) {
        Program program = programRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Program", "id", String.valueOf(id)));
        program.setTitle(programDto.getTitle());
        program.setDescription(programDto.getDescription());
        program.setPeriod(programDto.getPeriod());

        return mapper.mapToDto(programRepository.save(program));
    }
    @Override
    public ProgramDto deleteProgram(Long id) {
        ProgramDto programDto = getProgramById(id);
        programRepository.deleteById(id);
        return programDto;
    }

}
