package ua.foxminded.kucherenko.task2.generators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.kucherenko.task2.dao.StudentCourseDao;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class StudentCourseGeneratorTest {
    @Autowired
    private StudentCourseGenerator studentCourseGenerator;
    @Autowired
    private StudentsGenerator studentsGenerator;
    @Autowired
    private CoursesGenerator coursesGenerator;
    @MockBean
    private StudentCourseDao studentCourseDao;

    @BeforeEach
    void generateStudents() {
        MockitoAnnotations.initMocks(this);
        studentsGenerator.addToDb();
        coursesGenerator.addToDb();
        studentCourseGenerator.addToDb();
    }

    @Test
    @Sql("/database/create_tables.sql")
    void checkStudentsNumber() {
        final int studentCoursesListSize = 200;
        verify(studentCourseDao, atLeastOnce()).save(any());
    }
}
