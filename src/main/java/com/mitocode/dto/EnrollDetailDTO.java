package com.mitocode.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.mitocode.model.Enroll;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnrollDetailDTO {

    private Integer idEnrollDetail;

    @JsonBackReference
    private EnrollDTO enroll;

    @JsonIncludeProperties(value = {"idCourse"})
    @NotNull
    private CourseDTO course;

    @NotNull
    private String classroomDetail;
}
