package com.mitocode.controller;

import com.mitocode.dto.EnrollDTO;
import com.mitocode.model.Enroll;
import com.mitocode.service.IEnrollService;
import com.mitocode.service.impl.EnrollServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/enrolls")
@RequiredArgsConstructor
public class EnrollController {

    private final IEnrollService service;
    @Qualifier("defaultMapper")
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity <List<EnrollDTO>> readAll() throws Exception{
        List<EnrollDTO> list = service.readAll().stream().map(this::convertToDTO).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollDTO> redById(@PathVariable("id") Integer id) throws Exception{
        EnrollDTO obj = convertToDTO(service.readById(id));
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EnrollDTO> create(@Valid @RequestBody EnrollDTO dto) throws Exception{
            Enroll obj = service.save(convertToEntity(dto));
            return new ResponseEntity<>(convertToDTO(obj), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnrollDTO> update (@Valid @RequestBody EnrollDTO dto, @PathVariable("id") Integer id) throws Exception{
        dto.setIdEnroll(id);
        Enroll obj = service.update(convertToEntity(dto),id);
        return new ResponseEntity<>(convertToDTO(obj), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id")Integer id) throws Exception{
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/coursesandstudents")
    public ResponseEntity<Map<String, List<String>>>getStudentsByCourses(){
        Map<String, List<String>>list = service.getCoursesAndStudents();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    private EnrollDTO convertToDTO(Enroll obj){
        return mapper.map(obj, EnrollDTO.class);
    }
    private Enroll convertToEntity(EnrollDTO dto){
        return mapper.map(dto, Enroll.class);
    }
}
