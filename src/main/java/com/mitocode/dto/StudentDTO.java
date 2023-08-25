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
public class StudentDTO {

    private Integer idStudent;

    @NotNull
    @NotEmpty
    @Size(min = 6, max = 12)
    private String dniStudent;

    @Min(value = 6)
    @Max(value = 99)
    private int ageStudent;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 100)
    private String firstNameStudent;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 100)
    private String lastNameStudent;

}
