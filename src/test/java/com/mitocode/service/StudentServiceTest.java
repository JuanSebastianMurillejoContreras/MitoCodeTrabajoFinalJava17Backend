package com.mitocode.service;

import com.mitocode.model.Student;
import com.mitocode.repo.IStudentRepo;
import com.mitocode.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class StudentServiceTest {

    @MockBean
    private StudentServiceImpl service;

    @MockBean
    private IStudentRepo repo;

    private Student STUDENT_1;
    private Student STUDENT_2;
    private Student STUDENT_3;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(repo);
        this.service = new StudentServiceImpl(repo);

        Student STUDENT_1 = new Student(1, "1110556531", 28, "Juan Sebastian","Murillejo Contreras");
        Student STUDENT_2 = new Student(2, "65495628", 67, "Argenis","Contreras");
        Student STUDENT_3 = new Student(3, "14267307", 70, "Germán","Murillejo");

        List<Student> list = List.of(STUDENT_1, STUDENT_2, STUDENT_3);
        Mockito.when(repo.findAll()).thenReturn(list);
        Mockito.when(repo.findById(any())).thenReturn(Optional.of(STUDENT_1));
        Mockito.when(repo.save(any())).thenReturn(STUDENT_1);
    }

    @Test
    void findAllTest() throws Exception{
        List<Student> response = service.readAll();
        assertEquals(response.size(),3);
    }

    @Test
    void readByIDTest() throws Exception{
        final int ID = 1;
        Student response = service.readById(any());
        assertNotNull(response);
    }

    @Test
    void saveTest() throws Exception{
        Student respose = service.save(STUDENT_1);
        assertNotNull(respose);
    }

    @Test
    void updateTest() throws Exception{
        final int ID = 1;
        Student response = service.update(STUDENT_1, any());
        assertNotNull(response);
    }

    @Test
    void delete() throws Exception{
        repo.deleteById(any());
        repo.deleteById(any());
        repo.deleteById(any());
        verify(repo, times(3)).deleteById(any());
    }
}
