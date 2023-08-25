package com.mitocode.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mitocode.model.EnrollDetail;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnrollDTO {

    private Integer idEnroll;

    @NotNull
    @JsonIncludeProperties(value = {"idStudent"})
    private StudentDTO studentEnroll;

    @NotNull
    private LocalDateTime dateEnroll;

    private boolean statusEnroll;

    @JsonManagedReference
    @NotNull
    private List<EnrollDetailDTO> enrollDetails;





}
