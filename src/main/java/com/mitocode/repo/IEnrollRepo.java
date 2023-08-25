package com.mitocode.repo;

import com.mitocode.model.Enroll;
import com.mitocode.model.Student;

import java.util.List;

public interface IEnrollRepo extends IGenericRepo<Enroll, Integer>{
    List<Enroll> findAllByStudent(Student student);
}
