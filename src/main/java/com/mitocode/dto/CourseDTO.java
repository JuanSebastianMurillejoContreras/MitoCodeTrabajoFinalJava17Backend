package com.mitocode.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDTO {

    private Integer idCourse;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 45)
    private String nameCourse;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 10)
    private String acronymCourse;

    private boolean statusCourse;
}
