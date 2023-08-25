package com.mitocode.repo;

import com.mitocode.model.Student;

import java.util.List;

public interface IStudentRepo extends IGenericRepo <Student, Integer>{
    List<Student> findStudentByOrderByAge();
}
