package com.mitocode.service;

import com.mitocode.model.Course;
import com.mitocode.model.Enroll;
import com.mitocode.model.EnrollDetail;
import com.mitocode.model.Student;

import java.util.List;
import java.util.Map;

public interface IEnrollService extends ICRUD<Enroll, Integer>{
    Map<String, List<String>> getCoursesAndStudents();
}
