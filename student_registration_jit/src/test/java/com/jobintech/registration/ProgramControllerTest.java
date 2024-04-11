package com.jobintech.registration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.jobintech.registration.controller.ProgramController;
import com.jobintech.registration.dto.ProgramDto;
import com.jobintech.registration.payload.ProgramResponse;
import com.jobintech.registration.service.imp.ProgramServiceImp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(ProgramController.class)
public class ProgramControllerTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private ProgramServiceImp programServiceImp;


    @InjectMocks
    private ProgramController programController;

    ProgramDto program_1 = new ProgramDto(1L, "Java", "Description", "4 Months");
    ProgramDto program_2 = new ProgramDto(2L, ".Net", "Description 2", "5 Months");

    @Before
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(programController).build();
    }

    @Test
    public void testGetAllPrograms() throws Exception {
        // Mocking ProductResponse
        List<ProgramDto> programs = Arrays.asList(
                new ProgramDto(1L, "Product 1", "description 1", "4 months"),
                new ProgramDto(2L, "Product 2", "description 2", "5 months")

                );
        ProgramResponse programResponse = new ProgramResponse(programs, 0, 5, 2, 1);
        ResponseEntity<ProgramResponse> responseEntity = ResponseEntity.ok(programResponse);

        Mockito.when(programServiceImp.getProgramPage(Mockito.anyInt(), Mockito.anyInt())).thenReturn(programResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/programs/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].title", hasValue("Product 1")));
    }

    @Test
    public void getProgramById() throws Exception {

        Mockito.when(programServiceImp.getProgramById(program_1.getId())).thenReturn(program_1);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/programs/1")
                   .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Java")));
    }

    @Test
    public void createProgram() throws Exception {
        ProgramDto programDto = new ProgramDto(5L, "Java", "Description", "Period");

        Mockito.when(programServiceImp.createNewProgram(programDto)).thenReturn(programDto);

        String content = objectWriter.writeValueAsString(programDto);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.post("/api/v1/programs/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(content);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()));
    }



}
