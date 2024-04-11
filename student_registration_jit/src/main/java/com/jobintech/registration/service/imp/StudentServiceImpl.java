package com.jobintech.registration.service.imp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jobintech.registration.dto.StudentDto;
import com.jobintech.registration.dto.StudentResponse;
import com.jobintech.registration.entity.Student;
import com.jobintech.registration.enums.Status;
import com.jobintech.registration.exception.EmailExistException;
import com.jobintech.registration.exception.ResourceNotFoundException;
import com.jobintech.registration.mapper.ProgramMapper;
import com.jobintech.registration.mapper.StudentMapper;
import com.jobintech.registration.repository.StudentRepository;
import com.jobintech.registration.service.EmailService;
import com.jobintech.registration.service.FileService;
import com.jobintech.registration.service.SendService;
import com.jobintech.registration.service.StudentService;
import com.jobintech.registration.utils.EmailDetails;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class StudentServiceImpl implements StudentService {

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5 MB in bytes
    private final FileService fileService;
    private final StudentRepository studentRepository;
    private final ProgramMapper programMapper;
    private final StudentMapper studentMapper;

    private final EmailService emailService;
    private final SendService sendService;

    public StudentServiceImpl(FileService fileService, StudentRepository studentRepository, ProgramMapper programMapper, StudentMapper studentMapper, EmailService emailService, SendService sendService) {
        this.fileService = fileService;
        this.studentRepository = studentRepository;
        this.programMapper = programMapper;
        this.studentMapper = studentMapper;
        this.emailService = emailService;
        this.sendService = sendService;
    }

    @Override
    public StudentResponse getAllStudents(int page,int size,String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                :Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page,size, sort);
        Page<Student> students = studentRepository.findAll(pageable);

        List<Student> listOfStudents = students.getContent();

        List<StudentDto> content = listOfStudents.stream().map(studentMapper::mapToDto).toList();

        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setContent(content);
        studentResponse.setPage(students.getNumber());
        studentResponse.setSize(students.getSize());
        studentResponse.setTotalElement(students.getTotalElements());
        studentResponse.setTotalPage(students.getTotalPages());
        studentResponse.setLast(students.isLast());

        return studentResponse;
    }


    @Override
    public StudentDto getStudent(Long id) {
        Student student = studentRepository.findStudentById(id)
                                                .orElseThrow(
                                                        ()->new ResourceNotFoundException("Student","id",id.toString())
                                                );
        return studentMapper.mapToDto(student);
    }



    @Override
    @Transactional
    public CompletableFuture<StudentDto> createStudent(StudentDto studentDto, MultipartFile cv) {
        Student student = studentMapper.mapToEntity(studentDto);
        if (this.studentRepository.findStudentByEmail(student.getEmail()).isPresent())
            throw new EmailExistException(student.getEmail());
        student.setProgram(programMapper.mapToEntity(studentDto.getProgramDto()));
        String fileName = String.format("%s_%s_CV", studentDto.getLastName(), studentDto.getFirstName()).replaceAll("\\s+", "_");

        student.setCvName(fileName);
        StudentDto studentDto1 = studentMapper.mapToDto(studentRepository.save(student));
        //file upload
        this.fileService.uploadFile(cv, fileName);
        //email message;
        emailService.sendSimpleMail(new EmailDetails(
                studentDto1.getEmail(),
                String.format(
                        "Dear %s," +
                                "\n\nThank you for registering to %s program. Your registration is confirmed." +
                                "\nWe will review your application and contact you as soon as possible." +
                                "\n\nRegards," +
                                "\nJobInTech",
                        studentDto1.getFirstName(), studentDto1.getProgramDto().getTitle()),
                "Registration confirmation"
        ));
        return CompletableFuture.completedFuture(studentDto1);
    }

    @Override
    public StudentDto updateStudent(Long id, StudentDto studentDto) throws JsonProcessingException {
        StudentDto studentDto1 = getStudent(id);
        Student student = studentMapper.mapToEntity(studentDto);
        student.setId(id);
        studentRepository.save(student);
        if(studentDto1.getStatus() != studentDto.getStatus()){
            if(studentDto.getStatus() == Status.APPROVED){
                emailService.sendSimpleMail(new EmailDetails(
                        studentDto1.getEmail(),
                        String.format(
                                "Dear %s," +
                                        "\n\nWe are pleased to inform you that you have been accepted into the %s program." +
                                        "\n\nCongratulations on your acceptance! We look forward to welcoming you to our program." +
                                        "\n\nRegards," +
                                        "\nJobInTech",
                                studentDto1.getFirstName(), studentDto1.getProgramDto().getTitle()),
                        "Acceptance Notification"
                ));
                studentDto.setId(null);
                sendService.send("student",studentDto);
            }
            if(studentDto.getStatus() == Status.DECLINED) {
                // Sending email notification for rejection
                emailService.sendSimpleMail(new EmailDetails(
                        studentDto1.getEmail(),
                        String.format(
                                "Dear %s," +
                                        "\n\nWe regret to inform you that your application for the %s program has been rejected." +
                                        "\n\nWe appreciate your interest in our program and encourage you to consider applying again in the future." +
                                        "\n\nRegards," +
                                        "\nJobInTech",
                                studentDto1.getFirstName(), studentDto1.getProgramDto().getTitle()),
                        "Application Rejection Notification"
                ));
            }
        }
        return studentMapper.mapToDto(student);
    }

    @Override
    public StudentDto deleteStudent(Long id) {
        StudentDto studentDto = getStudent(id);
        this.studentRepository.deleteById(id);
        return studentDto;
    }

    @Override
    public ResponseEntity<Resource> downloadStudentCv(String cvName) throws IOException {
        return this.fileService.downloadFile(cvName);
    }

}
