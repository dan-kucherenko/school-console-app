package ua.foxminded.kucherenko.task2.generators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ua.foxminded.kucherenko.task2.dao.CourseDao;
import ua.foxminded.kucherenko.task2.dao.StudentCourseDao;
import ua.foxminded.kucherenko.task2.dao.StudentDao;

import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class StudentCourseGeneratorTest {
    @Autowired
    private StudentCourseGenerator studentCourseGenerator;
    @MockBean
    private StudentCourseDao studentCourseDao;
    @MockBean
    private StudentDao studentDao;
    @MockBean
    private CourseDao courseDao;

    @BeforeEach
    void generateStudents() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void checkStudentsNumber() {
        when(studentDao.getAllStudentIds()).thenReturn(IntStream.rangeClosed(1, 200).boxed().toList());
        when(courseDao.getAllCourseIds()).thenReturn(IntStream.rangeClosed(1, 10).boxed().toList());
        studentCourseGenerator.addToDb();
        verify(studentCourseDao, atLeastOnce()).save(any());
    }
}
