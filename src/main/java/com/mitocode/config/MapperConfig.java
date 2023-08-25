package com.mitocode.config;

import com.mitocode.dto.CourseDTO;
import com.mitocode.dto.EnrollDTO;
import com.mitocode.model.Course;
import com.mitocode.model.Enroll;
import com.mitocode.model.EnrollDetail;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean("courseMapper")
    public ModelMapper courseMapper(){
        ModelMapper mapper = new ModelMapper();
        TypeMap<Course, CourseDTO> typeMap1 = mapper.createTypeMap(Course.class, CourseDTO.class);
        typeMap1.addMapping(Course::getName, (dest, v) -> dest.setNameCourse((String)v));

        TypeMap<CourseDTO, Course> typeMap2 = mapper.createTypeMap(CourseDTO.class, Course.class);
        typeMap2.addMapping(CourseDTO::getNameCourse, (dest, v) -> dest.setName((String)v));
        return mapper;
    }

    @Bean
    public ModelMapper defaultMapper(){
        return new ModelMapper();
    }
}
