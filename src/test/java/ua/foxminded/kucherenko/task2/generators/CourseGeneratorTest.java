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
import ua.foxminded.kucherenko.task2.dao.CourseDao;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class CourseGeneratorTest {
    @Autowired
    private CoursesGenerator coursesGenerator;
    @MockBean
    private CourseDao courseDao;

    @BeforeEach
    void generateGroups() {
        MockitoAnnotations.initMocks(this);
        coursesGenerator.addToDb();
    }

    @Test
    @Sql("/database/create_tables.sql")
    void checkNumberOfCourses(){
        final int courseListSize = 10;
        verify(courseDao, times(courseListSize)).save(any());
    }
}
