package com.jobintech.registration.controller;

import com.jobintech.registration.dto.ProgramDto;
import com.jobintech.registration.payload.ProgramResponse;
import com.jobintech.registration.service.ProgramService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/programs")
public class ProgramController {

    private final ProgramService programService;

    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    /*@GetMapping("/all")
    public ResponseEntity<List<ProgramDto>> getAllPrograms(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "5") int pageSize


            ){
        return new ResponseEntity<>(programService.getPrograms(pageNumber, pageSize), HttpStatus.OK);
    }*/

    @GetMapping("/all")
    public ResponseEntity<ProgramResponse> getProgramPage(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "5") int pageSize
    ){
        return new ResponseEntity<>(programService.getProgramPage(pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgramDto> getProgramById(@PathVariable Long id){
        return new ResponseEntity<>(programService.getProgramById(id), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ProgramDto> createProgram(@RequestBody ProgramDto programDto){
        return new ResponseEntity<>(programService.createNewProgram(programDto), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProgramDto> updateProgram(
            @PathVariable Long id,
            @RequestBody ProgramDto programDto
    ){
        return new ResponseEntity<>(programService.updateProgram(id, programDto), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ProgramDto> deleteProgram(@PathVariable Long id){
        return new ResponseEntity<>(programService.deleteProgram(id), HttpStatus.OK);
    }
}
