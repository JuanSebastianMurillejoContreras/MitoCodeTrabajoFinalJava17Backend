package com.mitocode.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitocode.dto.CourseDTO;
import com.mitocode.exception.ModelNotFoundException;
import com.mitocode.model.Course;
import com.mitocode.service.ICourseService;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseController.class)
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICourseService service;

    @MockBean(name = "defaultMapper")
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    Course course_1 = new Course(1, "Java", "J", true);
    Course course_2 = new Course(2, "Python", "Py", true);
    Course course_3 = new Course(3, "Goland", "Go", true);
    CourseDTO courseDTO_1 = new CourseDTO(1, "Java", "J", true);
    CourseDTO courseDTO_2 = new CourseDTO(2, "Python", "Py", true);
    CourseDTO courseDTO_3 = new CourseDTO(3, "Goland", "Go", true);
    CourseDTO courseDTO_99 = new CourseDTO(99, "TypeScript", "TS", false);

    @Test
    void readAllTest() throws Exception{
        List<Course> lst = List.of(course_1,course_2, course_3);

        Mockito.when(service.readAll()).thenReturn(lst);
        Mockito.when(modelMapper.map(course_1, CourseDTO.class)).thenReturn(courseDTO_1);
        Mockito.when(modelMapper.map(course_2, CourseDTO.class)).thenReturn(courseDTO_2);
        Mockito.when(modelMapper.map(course_3, CourseDTO.class)).thenReturn(courseDTO_3);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/courses")
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[1].nameCourse",is("Python")));
    }

    @Test
    void readByIdTest()throws Exception{
        final int ID = 1;

        Mockito.when(service.readById(any())).thenReturn(course_2);
        Mockito.when(modelMapper.map(course_2, CourseDTO.class)).thenReturn(courseDTO_2);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/courses/" + ID)
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nameCourse", is("Python")));
    }

    @Test
    void createTest() throws Exception{
        Mockito.when(service.save(any())).thenReturn(course_3);
        Mockito.when(modelMapper.map(course_3, CourseDTO.class)).thenReturn(courseDTO_3);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(courseDTO_3));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nameCourse", is("Goland")))
                .andExpect(jsonPath("$.acronymCourse", is("Go")))
                .andExpect(jsonPath("$.statusCourse", is(true)));
    }

    @Test
    void updateTest() throws Exception{

        final int ID = 1;

        Mockito.when(service.update(any(), any())).thenReturn(course_1);
        Mockito.when(modelMapper.map(course_1, CourseDTO.class)).thenReturn(courseDTO_1);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/courses/" + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(courseDTO_1));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nameCourse", is("Java")))
                .andExpect(jsonPath("$.acronymCourse", is("J")))
                .andExpect(jsonPath("$.statusCourse", is(true)));

    }

    @Test
    void updateErrorTest() throws Exception{

        final int ID = 1;

        Mockito.when(service.update(any(), any())).thenThrow(new ModelNotFoundException("ID NOT VALID: " + ID));


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/courses/" + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(courseDTO_1));

        mockMvc.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ModelNotFoundException));


    }

    @Test
    void deleteTest() throws Exception{
        final int ID_COURSE = 1;

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/courses/" + ID_COURSE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteErrorTest() throws Exception{
        final int ID_COURSE = 999;

        Mockito.doThrow(new ModelNotFoundException("ID: " + ID_COURSE + "NOT FOUND")).when(service).delete(ID_COURSE);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/courses/" + ID_COURSE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ModelNotFoundException));
    }
}
