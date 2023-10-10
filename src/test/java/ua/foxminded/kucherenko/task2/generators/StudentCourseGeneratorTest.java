package ua.foxminded.kucherenko.task2.generators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ua.foxminded.kucherenko.task2.repositories.CourseRepository;
import ua.foxminded.kucherenko.task2.repositories.StudentRepository;
import ua.foxminded.kucherenko.task2.services.StudentCoursesService;

import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class StudentCourseGeneratorTest {
    @Autowired
    private StudentCourseGenerator studentCourseGenerator;
    @MockBean
    private StudentCoursesService studentCoursesService;
    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private CourseRepository courseRepository;

    @BeforeEach
    void generateStudents() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void checkStudentsNumber() {
        when(studentRepository.getAllStudentIds()).thenReturn(IntStream.rangeClosed(1, 200).boxed().toList());
        when(courseRepository.getAllCourseIds()).thenReturn(IntStream.rangeClosed(1, 10).boxed().toList());
        studentCourseGenerator.addToDb();
        verify(studentCoursesService, atLeastOnce()).addStudentToCourse(any());
    }
}
