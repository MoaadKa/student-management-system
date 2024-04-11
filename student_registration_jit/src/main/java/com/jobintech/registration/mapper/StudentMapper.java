package com.jobintech.registration.mapper;

import com.jobintech.registration.dto.StudentDto;
import com.jobintech.registration.entity.Program;
import com.jobintech.registration.entity.Student;
import com.jobintech.registration.service.ProgramService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class StudentMapper implements MapperInterface<Student, StudentDto>{

    private final ProgramMapper programMapper;
    private final ModelMapper modelMapper;

    public StudentMapper(ProgramMapper programMapper, ModelMapper modelMapper) {
        this.programMapper = programMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public Student mapToEntity(StudentDto dto) {
        Student student = modelMapper.map(dto,Student.class);
        student.setProgram(programMapper.mapToEntity(dto.getProgramDto()));
        return student;
    }

    @Override
    public StudentDto mapToDto(Student student) {
        StudentDto studentDto = modelMapper.map(student, StudentDto.class);
        studentDto.setProgramDto(programMapper.mapToDto(student.getProgram()));
        return studentDto;
    }
}
