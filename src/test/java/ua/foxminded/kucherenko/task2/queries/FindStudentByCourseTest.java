package ua.foxminded.kucherenko.task2.queries;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
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
    private FindStudentByCourse findStudentByCourse;

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
