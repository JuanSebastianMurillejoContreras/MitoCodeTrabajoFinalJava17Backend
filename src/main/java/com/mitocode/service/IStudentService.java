package com.mitocode.service;

import com.mitocode.model.Student;

import java.util.List;

public interface IStudentService extends ICRUD<Student, Integer>{
    List<Student> findStudentByOrderByAge();
}
