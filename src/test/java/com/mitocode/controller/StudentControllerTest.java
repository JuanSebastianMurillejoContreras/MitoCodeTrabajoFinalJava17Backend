package com.mitocode.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitocode.dto.StudentDTO;
import com.mitocode.exception.ModelNotFoundException;
import com.mitocode.model.Student;
import com.mitocode.service.IStudentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IStudentService service;

    @MockBean(name = "defaultMapper")
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    Student student_1 = new Student(1, "1110556531", 28, "Juan Sebastian","Murillejo Contreras");
    Student student_2 = new Student(2, "65495628", 67, "Argenis","Contreras");
    Student student_3 = new Student(3, "14267307", 70, "Germán","Murillejo");

    StudentDTO studentDTO_1 = new StudentDTO(1, "1110556531", 28, "Juan Sebastian","Murillejo Contreras");
    StudentDTO studentDTO_2 = new StudentDTO(2, "65495628", 67, "Argenis","Contreras");
    StudentDTO studentDTO_3 = new StudentDTO(3, "14267307", 70, "Germán","Murillejo");
    StudentDTO studentDTO_99 = new StudentDTO(99, "14267307", 70, "Germán","Murillejo");


    @Test
    void readAllTest() throws Exception{
        List<Student> lst = List.of(student_1,student_2, student_3);

        Mockito.when(service.readAll()).thenReturn(lst);
        Mockito.when(modelMapper.map(student_1, StudentDTO.class)).thenReturn(studentDTO_1);
        Mockito.when(modelMapper.map(student_2, StudentDTO.class)).thenReturn(studentDTO_2);
        Mockito.when(modelMapper.map(student_3, StudentDTO.class)).thenReturn(studentDTO_3);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/students")
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[1].firstNameStudent",is("Argenis")));
    }

    @Test
    void readByIdTest()throws Exception{
        final int ID = 1;

        Mockito.when(service.readById(any())).thenReturn(student_2);
        Mockito.when(modelMapper.map(student_2, StudentDTO.class)).thenReturn(studentDTO_2);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/students/" + ID)
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstNameStudent", is("Argenis")));
    }

    @Test
    void createTest() throws Exception{
        Mockito.when(service.save(any())).thenReturn(student_3);
        Mockito.when(modelMapper.map(student_3, StudentDTO.class)).thenReturn(studentDTO_3);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentDTO_3));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstNameStudent", is("Germán")))
                .andExpect(jsonPath("$.lastNameStudent", is("Murillejo")))
                .andExpect(jsonPath("$.ageStudent", is(70)));
    }

    @Test
    void updateTest() throws Exception{

        final int ID = 1;

        Mockito.when(service.update(any(), any())).thenReturn(student_1);
        Mockito.when(modelMapper.map(student_1, StudentDTO.class)).thenReturn(studentDTO_1);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/students/" + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentDTO_1));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstNameStudent", is("Juan Sebastian")))
                .andExpect(jsonPath("$.lastNameStudent", is("Murillejo Contreras")))
                .andExpect(jsonPath("$.ageStudent", is(28)));

    }

    @Test
    void updateErrorTest() throws Exception{

        final int ID = 99;

        Mockito.when(service.update(any(), any())).thenThrow(new ModelNotFoundException("ID NOT VALID: " + ID));


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/students/" + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentDTO_99));

        mockMvc.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ModelNotFoundException));


    }

    @Test
    void deleteTest() throws Exception{
        final int ID_STUDENT = 1;

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/students/" + ID_STUDENT)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteErrorTest() throws Exception{
        final int ID_STUDENT = 999;

        Mockito.doThrow(new ModelNotFoundException("ID: " + ID_STUDENT + "NOT FOUND")).when(service).delete(ID_STUDENT);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/students/" + ID_STUDENT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ModelNotFoundException));
    }
}
