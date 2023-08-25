package com.mitocode.service;

import com.mitocode.model.Course;
import com.mitocode.repo.ICourseRepo;
import com.mitocode.service.impl.CourseServiceImpl;
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
public class CourseServiceTest {

    @MockBean
    private CourseServiceImpl service;

    @MockBean
    private ICourseRepo repo;

    private Course COURSE_1;
    private Course COURSE_2;
    private Course COURSE_3;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(repo);
        this.service = new CourseServiceImpl(repo);

        Course COURSE_1 = new Course(1, "Java", "J", true);
        Course COURSE_2 = new Course(2, "Python", "Py", true);
        Course COURSE_3 = new Course(3, "Goland", "Go", true);

        List<Course> list = List.of(COURSE_1, COURSE_2, COURSE_3);
        Mockito.when(repo.findAll()).thenReturn(list);
        Mockito.when(repo.findById(any())).thenReturn(Optional.of(COURSE_1));
        Mockito.when(repo.save(any())).thenReturn(COURSE_1);
    }

    @Test
    void findAllTest() throws Exception{
        List<Course> response = service.readAll();
        assertEquals(response.size(),3);
    }

    @Test
    void readByIDTest() throws Exception{
        final int ID = 1;
        Course response = service.readById(any());
        assertNotNull(response);
    }

    @Test
    void saveTest() throws Exception{
        Course respose = service.save(COURSE_1);
        assertNotNull(respose);
    }

    @Test
    void updateTest() throws Exception{
        final int ID = 1;
        Course response = service.update(COURSE_1, any());
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
