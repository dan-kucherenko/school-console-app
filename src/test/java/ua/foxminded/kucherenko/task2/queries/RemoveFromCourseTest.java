package ua.foxminded.kucherenko.task2.queries;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.kucherenko.task2.dao.StudentCourseDao;
import ua.foxminded.kucherenko.task2.dao.StudentDao;
import ua.foxminded.kucherenko.task2.queries.remove_from_course.RemoveFromCourse;
import ua.foxminded.kucherenko.task2.queries.remove_from_course.RemoveFromCourseData;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class RemoveFromCourseTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private RemoveFromCourse removeFromCourse;
    private StudentDao studentDao;
    private StudentCourseDao studentCourseDao;

    @BeforeEach
    void initTestData() {
        removeFromCourse = new RemoveFromCourse(jdbcTemplate);
        studentDao = new StudentDao(jdbcTemplate);
        studentCourseDao = new StudentCourseDao(jdbcTemplate);
    }

    @Test
    @Sql({"/database/create_tables.sql", "/database/clear_tables.sql"})
    @Sql({"/sample_data/students_samples.sql", "/sample_data/courses_samples.sql", "/sample_data/student_courses_samples.sql"})
    void removeFromCourse_ShouldntThrowException() {
        final String firstName = "Emma";
        final String lastName = "Emmson";
        final int courseId = 5;
        RemoveFromCourseData data = new RemoveFromCourseData(firstName, lastName, courseId);

        Assertions.assertDoesNotThrow(() -> removeFromCourse.executeQuery(data));
        Assertions.assertFalse(() -> {
            Integer studentId = studentDao.getIdByName(firstName, lastName);
            return studentCourseDao.exists(studentId, courseId);
        });
    }

    @Test
    void removeFromCourse_NonExistingStudent_ShouldThrowException() {
        final String firstName = "Petro";
        final String lastName = "Petrovych";
        final int courseId = 5;
        RemoveFromCourseData data = new RemoveFromCourseData(firstName, lastName, courseId);

        Assertions.assertThrows(IllegalArgumentException.class, () -> removeFromCourse.executeQuery(data));
    }

    @Test
    void removeFromCourse_NonExistingCourse_ShouldThrowException() {
        final String firstName = "Petro";
        final String lastName = "Petrovych";
        final int courseId = -5;
        RemoveFromCourseData data = new RemoveFromCourseData(firstName, lastName, courseId);

        Assertions.assertThrows(IllegalArgumentException.class, () -> removeFromCourse.executeQuery(data));
    }
}
