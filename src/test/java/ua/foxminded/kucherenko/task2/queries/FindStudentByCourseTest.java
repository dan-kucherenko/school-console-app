package ua.foxminded.kucherenko.task2.queries;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.kucherenko.task2.models.Student;
import ua.foxminded.kucherenko.task2.queries.find_stud_by_course.FindStudentByCourse;
import ua.foxminded.kucherenko.task2.queries.find_stud_by_course.FindStudentByCourseData;


import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@Sql({"/database/create_tables.sql", "/database/clear_tables.sql"})
class FindStudentByCourseTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private FindStudentByCourse findStudentByCourse;

    @BeforeEach
    void setUp() {
        findStudentByCourse = new FindStudentByCourse(jdbcTemplate);
    }

    @Test
    @Sql({"/sample_data/students_samples.sql", "/sample_data/courses_samples.sql",
            "/sample_data/student_courses_samples.sql"})
    void findStudentByCourse_ShouldntThrowException() {
        final String courseName = "Physics";
        FindStudentByCourseData data = new FindStudentByCourseData(courseName);
        List<Student> expectedRes = List.of(
                new Student(4, 4, "Alice", "Alison")
        );
        List<Student> actualRes = findStudentByCourse.executeQueryWithRes(data);

        Assertions.assertEquals(expectedRes, actualRes);
    }

    @Test
    void findStudentByCourse_EmptyList_ShouldntThrowException() {
        final String courseName = "Music";
        FindStudentByCourseData data = new FindStudentByCourseData(courseName);
        List<Student> actualRes = findStudentByCourse.executeQueryWithRes(data);

        Assertions.assertTrue(actualRes.isEmpty());
    }

    @Test
    void findStudentByCourse_MissingCourse_ShouldntThrowException() {
        final String courseName = " ";
        FindStudentByCourseData data = new FindStudentByCourseData(courseName);

        Assertions.assertThrows(IllegalArgumentException.class, () -> findStudentByCourse.executeQueryWithRes(data));
    }
}
