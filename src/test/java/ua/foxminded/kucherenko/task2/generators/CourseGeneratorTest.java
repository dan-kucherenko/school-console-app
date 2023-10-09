package ua.foxminded.kucherenko.task2.generators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ua.foxminded.kucherenko.task2.repositories.CourseRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class CourseGeneratorTest {
    @Autowired
    private CoursesGenerator coursesGenerator;
    @MockBean
    private CourseRepository courseRepository;

    @BeforeEach
    void generateGroups() {
        MockitoAnnotations.initMocks(this);
        coursesGenerator.addToDb();
    }

    @Test
    void checkNumberOfCourses(){
        final int courseListSize = 10;
        verify(courseRepository, times(courseListSize)).save(any());
    }
}
